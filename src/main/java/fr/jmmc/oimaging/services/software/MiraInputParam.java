/*******************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ******************************************************************************/
package fr.jmmc.oimaging.services.software;

import java.util.List;

import fr.jmmc.oitools.image.ImageOiConstants;
import fr.jmmc.oitools.image.ImageOiInputParam;
import fr.jmmc.oitools.meta.KeywordMeta;
import fr.jmmc.oitools.meta.Types;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Specific parameters for MiRA

see  MiRA/src/mira2_image.i
func mira_read_input_params(src)
      threshold        = mira_get_fits_real(     fh, "SOFT_CUT"),
      recenter         = mira_get_fits_logical(  fh, "RECENTER"),
      bootstrap        = mira_get_fits_integer(  fh, "REPEAT"),

      regul            = mira_get_fits_string(   fh, "RGL_NAME", lower=1),
      mu               = mira_get_fits_real(     fh, "RGL_WGT"),
      tau              = mira_get_fits_real(     fh, "RGL_TAU"),
      gamma            = mira_get_fits_real(     fh, "RGL_GAMM"),

      flux             = mira_get_fits_real(     fh, "FLUX"),
      fluxerr          = mira_get_fits_real(     fh, "FLUXERR"),

      "min",             mira_get_fits_real(     fh, "PXL_MIN"),
      "max",             mira_get_fits_real(     fh, "PXL_MAX"),

      xform            = mira_get_fits_string(   fh, "XFORM"),

      smearingfunction = mira_get_fits_string(   fh, "SMEAR_FN"),
      smearingfactor   = mira_get_fits_real(     fh, "SMEAR_FC"),

      maxeval          = mira_get_fits_integer(  fh, "MAXEVAL"),
      maxiter          = mira_get_fits_integer(  fh, "MAXITER"),

      mem              = mira_get_fits_integer(  fh, "OPT_MEM"),
      ftol             = mira_get_fits_real(     fh, "OPT_FTOL"),
      gtol             = mira_get_fits_real(     fh, "OPT_GTOL"),
      sftol            = mira_get_fits_real(     fh, "LNS_FTOL"),
      sgtol            = mira_get_fits_real(     fh, "LNS_GTOL"),
      sxtol            = mira_get_fits_real(     fh, "LNS_XTOL");

-regul=compactness -mu=1E6 -gamma=6mas 
 */
public final class MiraInputParam extends SoftwareInputParam {

    private static final String DEFAULT_CLI_OPTIONS = "-recenter -bootstrap=2 -verb=1";

    public static final Set<String> SUPPORTED_STD_KEYWORDS = new HashSet<String>(Arrays.asList(new String[]{
        ImageOiConstants.KEYWORD_MAXITER, // -maxiter
        ImageOiConstants.KEYWORD_RGL_NAME, // -regul
        ImageOiConstants.KEYWORD_RGL_WGT, // -mu
        ImageOiConstants.KEYWORD_FLUX,
        ImageOiConstants.KEYWORD_FLUXERR
    }));

    public static final String KEYWORD_SMEAR_FN = "SMEAR_FN";
    public static final String KEYWORD_SMEAR_FC = "SMEAR_FC";
//    public static final String KEYWORD_XFORM = "XFORM";

    public static final String[] KEYWORD_SMEAR_FN_LIST = new String[]{"none", "sinc", "gauss"};
    public static final String[] KEYWORD_XFORM_LIST = new String[]{"nfft", "separable", "nonseparable"};

    // optional
    public static final String KEYWORD_RGL_TAU = "RGL_TAU"; // - tau
    public static final String KEYWORD_RGL_GAMM = "RGL_GAMM"; // -gamma

    // optional
    private static final KeywordMeta RGL_TAU = new KeywordMeta(KEYWORD_RGL_TAU,
            "Scalar factor for hyperbolic L1-L2 regularization, used to set the threshold "
            + "between quadratic (l2) and linear (L1) regularizations", Types.TYPE_DBL);
    private static final KeywordMeta RGL_GAMM = new KeywordMeta(KEYWORD_RGL_GAMM,
            "A priori full half width at half maximum for compactness", Types.TYPE_DBL);

    private static final KeywordMeta SMEAR_FN = new KeywordMeta(KEYWORD_SMEAR_FN,
            "Bandwidth smearing function", Types.TYPE_CHAR, KEYWORD_SMEAR_FN_LIST);
    private static final KeywordMeta SMEAR_FC = new KeywordMeta(KEYWORD_SMEAR_FC,
            "Bandwidth smearing factor", Types.TYPE_DBL);
    /*
    private static final KeywordMeta XFORM = new KeywordMeta(KEYWORD_XFORM,
            "the name of the model to use for computing the nonuniform Fourier transform",
            Types.TYPE_CHAR, KEYWORD_XFORM_LIST);
     */
    // Potential Conflict with ImageOiInputParam.KEYWORD_RGL_NAME ?
    public static final String[] RGL_NAME_MIRA = new String[]{"compactness", "hyperbolic"};

    public MiraInputParam() {
        super();
    }

    @Override
    public void update(final ImageOiInputParam params, final boolean applyDefaults) {
        super.update(params, applyDefaults);

        // define keywords:
        // for our first implementation, just add to params if not TOTVAR
        if (params.getRglName().startsWith("compactness")) {
            params.addKeyword(RGL_GAMM);
            params.setKeywordDefaultDouble(KEYWORD_RGL_GAMM, 6.0); // unit is mas (conversion ?)
        } else if (params.getRglName().startsWith("hyperbolic")) {
            params.addKeyword(RGL_TAU);
            params.setKeywordDefaultDouble(KEYWORD_RGL_TAU, 1.0);
        }

        params.addKeyword(SMEAR_FN);
        params.addKeyword(SMEAR_FC);
//        params.addKeyword(XFORM);

        // default values:
        if (applyDefaults) {
            // specific default values for MiRA:
            params.setRglWgt(1E6); // -mu=1E6
        }
        params.setKeywordDefaultDouble(KEYWORD_SMEAR_FC, 1.0);
        params.setKeywordDefault(KEYWORD_SMEAR_FN, KEYWORD_SMEAR_FN_LIST[0]); // none
//        params.setKeywordDefault(KEYWORD_XFORM, KEYWORD_XFORM_LIST[0]); // nfft
    }

    @Override
    public void validate(final ImageOiInputParam params, final List<String> failures) {
        // custom validation rules:
        // TODO
    }

    public String getDefaultCliOptions() {
        return DEFAULT_CLI_OPTIONS;
    }

    @Override
    public String[] getSupported_RGL_NAME() {
        return RGL_NAME_MIRA;
    }

    @Override
    public boolean supportsStandardKeyword(final String keywordName) {
        return SUPPORTED_STD_KEYWORDS.contains(keywordName);
    }
}
