package tk.ivybits.engine.gl;

import org.lwjgl.PointerBuffer;
import org.lwjgl.PointerWrapper;
import org.lwjgl.opengl.*;

import java.nio.*;

public class GL {
    public static int GL_ACCUM = 256;
    public static int GL_LOAD = 257;
    public static int GL_RETURN = 258;
    public static int GL_MULT = 259;
    public static int GL_ADD = 260;
    public static int GL_NEVER = 512;
    public static int GL_LESS = 513;
    public static int GL_EQUAL = 514;
    public static int GL_LEQUAL = 515;
    public static int GL_GREATER = 516;
    public static int GL_NOTEQUAL = 517;
    public static int GL_GEQUAL = 518;
    public static int GL_ALWAYS = 519;
    public static int GL_CURRENT_BIT = 1;
    public static int GL_POINT_BIT = 2;
    public static int GL_LINE_BIT = 4;
    public static int GL_POLYGON_BIT = 8;
    public static int GL_POLYGON_STIPPLE_BIT = 16;
    public static int GL_PIXEL_MODE_BIT = 32;
    public static int GL_LIGHTING_BIT = 64;
    public static int GL_FOG_BIT = 128;
    public static int GL_DEPTH_BUFFER_BIT = 256;
    public static int GL_ACCUM_BUFFER_BIT = 512;
    public static int GL_STENCIL_BUFFER_BIT = 1024;
    public static int GL_VIEWPORT_BIT = 2048;
    public static int GL_TRANSFORM_BIT = 4096;
    public static int GL_ENABLE_BIT = 8192;
    public static int GL_COLOR_BUFFER_BIT = 16384;
    public static int GL_HINT_BIT = 32768;
    public static int GL_EVAL_BIT = 65536;
    public static int GL_LIST_BIT = 131072;
    public static int GL_TEXTURE_BIT = 262144;
    public static int GL_SCISSOR_BIT = 524288;
    public static int GL_ALL_ATTRIB_BITS = 1048575;
    public static int GL_POINTS = 0;
    public static int GL_LINES = 1;
    public static int GL_LINE_LOOP = 2;
    public static int GL_LINE_STRIP = 3;
    public static int GL_TRIANGLES = 4;
    public static int GL_TRIANGLE_STRIP = 5;
    public static int GL_TRIANGLE_FAN = 6;
    public static int GL_QUADS = 7;
    public static int GL_QUAD_STRIP = 8;
    public static int GL_POLYGON = 9;
    public static int GL_ZERO = 0;
    public static int GL_ONE = 1;
    public static int GL_SRC_COLOR = 768;
    public static int GL_ONE_MINUS_SRC_COLOR = 769;
    public static int GL_SRC_ALPHA = 770;
    public static int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static int GL_DST_ALPHA = 772;
    public static int GL_ONE_MINUS_DST_ALPHA = 773;
    public static int GL_DST_COLOR = 774;
    public static int GL_ONE_MINUS_DST_COLOR = 775;
    public static int GL_SRC_ALPHA_SATURATE = 776;
    public static int GL_CONSTANT_COLOR = 32769;
    public static int GL_ONE_MINUS_CONSTANT_COLOR = 32770;
    public static int GL_CONSTANT_ALPHA = 32771;
    public static int GL_ONE_MINUS_CONSTANT_ALPHA = 32772;
    public static int GL_TRUE = 1;
    public static int GL_FALSE = 0;
    public static int GL_CLIP_PLANE0 = 12288;
    public static int GL_CLIP_PLANE1 = 12289;
    public static int GL_CLIP_PLANE2 = 12290;
    public static int GL_CLIP_PLANE3 = 12291;
    public static int GL_CLIP_PLANE4 = 12292;
    public static int GL_CLIP_PLANE5 = 12293;
    public static int GL_BYTE = 5120;
    public static int GL_UNSIGNED_BYTE = 5121;
    public static int GL_SHORT = 5122;
    public static int GL_UNSIGNED_SHORT = 5123;
    public static int GL_INT = 5124;
    public static int GL_UNSIGNED_INT = 5125;
    public static int GL_FLOAT = 5126;
    public static int GL_2_BYTES = 5127;
    public static int GL_3_BYTES = 5128;
    public static int GL_4_BYTES = 5129;
    public static int GL_DOUBLE = 5130;
    public static int GL_NONE = 0;
    public static int GL_FRONT_LEFT = 1024;
    public static int GL_FRONT_RIGHT = 1025;
    public static int GL_BACK_LEFT = 1026;
    public static int GL_BACK_RIGHT = 1027;
    public static int GL_FRONT = 1028;
    public static int GL_BACK = 1029;
    public static int GL_LEFT = 1030;
    public static int GL_RIGHT = 1031;
    public static int GL_FRONT_AND_BACK = 1032;
    public static int GL_AUX0 = 1033;
    public static int GL_AUX1 = 1034;
    public static int GL_AUX2 = 1035;
    public static int GL_AUX3 = 1036;
    public static int GL_NO_ERROR = 0;
    public static int GL_INVALID_ENUM = 1280;
    public static int GL_INVALID_VALUE = 1281;
    public static int GL_INVALID_OPERATION = 1282;
    public static int GL_STACK_OVERFLOW = 1283;
    public static int GL_STACK_UNDERFLOW = 1284;
    public static int GL_OUT_OF_MEMORY = 1285;
    public static int GL_2D = 1536;
    public static int GL_3D = 1537;
    public static int GL_3D_COLOR = 1538;
    public static int GL_3D_COLOR_TEXTURE = 1539;
    public static int GL_4D_COLOR_TEXTURE = 1540;
    public static int GL_PASS_THROUGH_TOKEN = 1792;
    public static int GL_POINT_TOKEN = 1793;
    public static int GL_LINE_TOKEN = 1794;
    public static int GL_POLYGON_TOKEN = 1795;
    public static int GL_BITMAP_TOKEN = 1796;
    public static int GL_DRAW_PIXEL_TOKEN = 1797;
    public static int GL_COPY_PIXEL_TOKEN = 1798;
    public static int GL_LINE_RESET_TOKEN = 1799;
    public static int GL_EXP = 2048;
    public static int GL_EXP2 = 2049;
    public static int GL_CW = 2304;
    public static int GL_CCW = 2305;
    public static int GL_COEFF = 2560;
    public static int GL_ORDER = 2561;
    public static int GL_DOMAIN = 2562;
    public static int GL_CURRENT_COLOR = 2816;
    public static int GL_CURRENT_INDEX = 2817;
    public static int GL_CURRENT_NORMAL = 2818;
    public static int GL_CURRENT_TEXTURE_COORDS = 2819;
    public static int GL_CURRENT_RASTER_COLOR = 2820;
    public static int GL_CURRENT_RASTER_INDEX = 2821;
    public static int GL_CURRENT_RASTER_TEXTURE_COORDS = 2822;
    public static int GL_CURRENT_RASTER_POSITION = 2823;
    public static int GL_CURRENT_RASTER_POSITION_VALID = 2824;
    public static int GL_CURRENT_RASTER_DISTANCE = 2825;
    public static int GL_POINT_SMOOTH = 2832;
    public static int GL_POINT_SIZE = 2833;
    public static int GL_POINT_SIZE_RANGE = 2834;
    public static int GL_POINT_SIZE_GRANULARITY = 2835;
    public static int GL_LINE_SMOOTH = 2848;
    public static int GL_LINE_WIDTH = 2849;
    public static int GL_LINE_WIDTH_RANGE = 2850;
    public static int GL_LINE_WIDTH_GRANULARITY = 2851;
    public static int GL_LINE_STIPPLE = 2852;
    public static int GL_LINE_STIPPLE_PATTERN = 2853;
    public static int GL_LINE_STIPPLE_REPEAT = 2854;
    public static int GL_LIST_MODE = 2864;
    public static int GL_MAX_LIST_NESTING = 2865;
    public static int GL_LIST_BASE = 2866;
    public static int GL_LIST_INDEX = 2867;
    public static int GL_POLYGON_MODE = 2880;
    public static int GL_POLYGON_SMOOTH = 2881;
    public static int GL_POLYGON_STIPPLE = 2882;
    public static int GL_EDGE_FLAG = 2883;
    public static int GL_CULL_FACE = 2884;
    public static int GL_CULL_FACE_MODE = 2885;
    public static int GL_FRONT_FACE = 2886;
    public static int GL_LIGHTING = 2896;
    public static int GL_LIGHT_MODEL_LOCAL_VIEWER = 2897;
    public static int GL_LIGHT_MODEL_TWO_SIDE = 2898;
    public static int GL_LIGHT_MODEL_AMBIENT = 2899;
    public static int GL_SHADE_MODEL = 2900;
    public static int GL_COLOR_MATERIAL_FACE = 2901;
    public static int GL_COLOR_MATERIAL_PARAMETER = 2902;
    public static int GL_COLOR_MATERIAL = 2903;
    public static int GL_FOG = 2912;
    public static int GL_FOG_INDEX = 2913;
    public static int GL_FOG_DENSITY = 2914;
    public static int GL_FOG_START = 2915;
    public static int GL_FOG_END = 2916;
    public static int GL_FOG_MODE = 2917;
    public static int GL_FOG_COLOR = 2918;
    public static int GL_DEPTH_RANGE = 2928;
    public static int GL_DEPTH_TEST = 2929;
    public static int GL_DEPTH_WRITEMASK = 2930;
    public static int GL_DEPTH_CLEAR_VALUE = 2931;
    public static int GL_DEPTH_FUNC = 2932;
    public static int GL_ACCUM_CLEAR_VALUE = 2944;
    public static int GL_STENCIL_TEST = 2960;
    public static int GL_STENCIL_CLEAR_VALUE = 2961;
    public static int GL_STENCIL_FUNC = 2962;
    public static int GL_STENCIL_VALUE_MASK = 2963;
    public static int GL_STENCIL_FAIL = 2964;
    public static int GL_STENCIL_PASS_DEPTH_FAIL = 2965;
    public static int GL_STENCIL_PASS_DEPTH_PASS = 2966;
    public static int GL_STENCIL_REF = 2967;
    public static int GL_STENCIL_WRITEMASK = 2968;
    public static int GL_MATRIX_MODE = 2976;
    public static int GL_NORMALIZE = 2977;
    public static int GL_VIEWPORT = 2978;
    public static int GL_MODELVIEW_STACK_DEPTH = 2979;
    public static int GL_PROJECTION_STACK_DEPTH = 2980;
    public static int GL_TEXTURE_STACK_DEPTH = 2981;
    public static int GL_MODELVIEW_MATRIX = 2982;
    public static int GL_PROJECTION_MATRIX = 2983;
    public static int GL_TEXTURE_MATRIX = 2984;
    public static int GL_ATTRIB_STACK_DEPTH = 2992;
    public static int GL_CLIENT_ATTRIB_STACK_DEPTH = 2993;
    public static int GL_ALPHA_TEST = 3008;
    public static int GL_ALPHA_TEST_FUNC = 3009;
    public static int GL_ALPHA_TEST_REF = 3010;
    public static int GL_DITHER = 3024;
    public static int GL_BLEND_DST = 3040;
    public static int GL_BLEND_SRC = 3041;
    public static int GL_BLEND = 3042;
    public static int GL_LOGIC_OP_MODE = 3056;
    public static int GL_INDEX_LOGIC_OP = 3057;
    public static int GL_COLOR_LOGIC_OP = 3058;
    public static int GL_AUX_BUFFERS = 3072;
    public static int GL_DRAW_BUFFER = 3073;
    public static int GL_READ_BUFFER = 3074;
    public static int GL_SCISSOR_BOX = 3088;
    public static int GL_SCISSOR_TEST = 3089;
    public static int GL_INDEX_CLEAR_VALUE = 3104;
    public static int GL_INDEX_WRITEMASK = 3105;
    public static int GL_COLOR_CLEAR_VALUE = 3106;
    public static int GL_COLOR_WRITEMASK = 3107;
    public static int GL_INDEX_MODE = 3120;
    public static int GL_RGBA_MODE = 3121;
    public static int GL_DOUBLEBUFFER = 3122;
    public static int GL_STEREO = 3123;
    public static int GL_RENDER_MODE = 3136;
    public static int GL_PERSPECTIVE_CORRECTION_HINT = 3152;
    public static int GL_POINT_SMOOTH_HINT = 3153;
    public static int GL_LINE_SMOOTH_HINT = 3154;
    public static int GL_POLYGON_SMOOTH_HINT = 3155;
    public static int GL_FOG_HINT = 3156;
    public static int GL_TEXTURE_GEN_S = 3168;
    public static int GL_TEXTURE_GEN_T = 3169;
    public static int GL_TEXTURE_GEN_R = 3170;
    public static int GL_TEXTURE_GEN_Q = 3171;
    public static int GL_PIXEL_MAP_I_TO_I = 3184;
    public static int GL_PIXEL_MAP_S_TO_S = 3185;
    public static int GL_PIXEL_MAP_I_TO_R = 3186;
    public static int GL_PIXEL_MAP_I_TO_G = 3187;
    public static int GL_PIXEL_MAP_I_TO_B = 3188;
    public static int GL_PIXEL_MAP_I_TO_A = 3189;
    public static int GL_PIXEL_MAP_R_TO_R = 3190;
    public static int GL_PIXEL_MAP_G_TO_G = 3191;
    public static int GL_PIXEL_MAP_B_TO_B = 3192;
    public static int GL_PIXEL_MAP_A_TO_A = 3193;
    public static int GL_PIXEL_MAP_I_TO_I_SIZE = 3248;
    public static int GL_PIXEL_MAP_S_TO_S_SIZE = 3249;
    public static int GL_PIXEL_MAP_I_TO_R_SIZE = 3250;
    public static int GL_PIXEL_MAP_I_TO_G_SIZE = 3251;
    public static int GL_PIXEL_MAP_I_TO_B_SIZE = 3252;
    public static int GL_PIXEL_MAP_I_TO_A_SIZE = 3253;
    public static int GL_PIXEL_MAP_R_TO_R_SIZE = 3254;
    public static int GL_PIXEL_MAP_G_TO_G_SIZE = 3255;
    public static int GL_PIXEL_MAP_B_TO_B_SIZE = 3256;
    public static int GL_PIXEL_MAP_A_TO_A_SIZE = 3257;
    public static int GL_UNPACK_SWAP_BYTES = 3312;
    public static int GL_UNPACK_LSB_FIRST = 3313;
    public static int GL_UNPACK_ROW_LENGTH = 3314;
    public static int GL_UNPACK_SKIP_ROWS = 3315;
    public static int GL_UNPACK_SKIP_PIXELS = 3316;
    public static int GL_UNPACK_ALIGNMENT = 3317;
    public static int GL_PACK_SWAP_BYTES = 3328;
    public static int GL_PACK_LSB_FIRST = 3329;
    public static int GL_PACK_ROW_LENGTH = 3330;
    public static int GL_PACK_SKIP_ROWS = 3331;
    public static int GL_PACK_SKIP_PIXELS = 3332;
    public static int GL_PACK_ALIGNMENT = 3333;
    public static int GL_MAP_COLOR = 3344;
    public static int GL_MAP_STENCIL = 3345;
    public static int GL_INDEX_SHIFT = 3346;
    public static int GL_INDEX_OFFSET = 3347;
    public static int GL_RED_SCALE = 3348;
    public static int GL_RED_BIAS = 3349;
    public static int GL_ZOOM_X = 3350;
    public static int GL_ZOOM_Y = 3351;
    public static int GL_GREEN_SCALE = 3352;
    public static int GL_GREEN_BIAS = 3353;
    public static int GL_BLUE_SCALE = 3354;
    public static int GL_BLUE_BIAS = 3355;
    public static int GL_ALPHA_SCALE = 3356;
    public static int GL_ALPHA_BIAS = 3357;
    public static int GL_DEPTH_SCALE = 3358;
    public static int GL_DEPTH_BIAS = 3359;
    public static int GL_MAX_EVAL_ORDER = 3376;
    public static int GL_MAX_LIGHTS = 3377;
    public static int GL_MAX_CLIP_PLANES = 3378;
    public static int GL_MAX_TEXTURE_SIZE = 3379;
    public static int GL_MAX_PIXEL_MAP_TABLE = 3380;
    public static int GL_MAX_ATTRIB_STACK_DEPTH = 3381;
    public static int GL_MAX_MODELVIEW_STACK_DEPTH = 3382;
    public static int GL_MAX_NAME_STACK_DEPTH = 3383;
    public static int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
    public static int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
    public static int GL_MAX_VIEWPORT_DIMS = 3386;
    public static int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 3387;
    public static int GL_SUBPIXEL_BITS = 3408;
    public static int GL_INDEX_BITS = 3409;
    public static int GL_RED_BITS = 3410;
    public static int GL_GREEN_BITS = 3411;
    public static int GL_BLUE_BITS = 3412;
    public static int GL_ALPHA_BITS = 3413;
    public static int GL_DEPTH_BITS = 3414;
    public static int GL_STENCIL_BITS = 3415;
    public static int GL_ACCUM_RED_BITS = 3416;
    public static int GL_ACCUM_GREEN_BITS = 3417;
    public static int GL_ACCUM_BLUE_BITS = 3418;
    public static int GL_ACCUM_ALPHA_BITS = 3419;
    public static int GL_NAME_STACK_DEPTH = 3440;
    public static int GL_AUTO_NORMAL = 3456;
    public static int GL_MAP1_COLOR_4 = 3472;
    public static int GL_MAP1_INDEX = 3473;
    public static int GL_MAP1_NORMAL = 3474;
    public static int GL_MAP1_TEXTURE_COORD_1 = 3475;
    public static int GL_MAP1_TEXTURE_COORD_2 = 3476;
    public static int GL_MAP1_TEXTURE_COORD_3 = 3477;
    public static int GL_MAP1_TEXTURE_COORD_4 = 3478;
    public static int GL_MAP1_VERTEX_3 = 3479;
    public static int GL_MAP1_VERTEX_4 = 3480;
    public static int GL_MAP2_COLOR_4 = 3504;
    public static int GL_MAP2_INDEX = 3505;
    public static int GL_MAP2_NORMAL = 3506;
    public static int GL_MAP2_TEXTURE_COORD_1 = 3507;
    public static int GL_MAP2_TEXTURE_COORD_2 = 3508;
    public static int GL_MAP2_TEXTURE_COORD_3 = 3509;
    public static int GL_MAP2_TEXTURE_COORD_4 = 3510;
    public static int GL_MAP2_VERTEX_3 = 3511;
    public static int GL_MAP2_VERTEX_4 = 3512;
    public static int GL_MAP1_GRID_DOMAIN = 3536;
    public static int GL_MAP1_GRID_SEGMENTS = 3537;
    public static int GL_MAP2_GRID_DOMAIN = 3538;
    public static int GL_MAP2_GRID_SEGMENTS = 3539;
    public static int GL_TEXTURE_1D = 3552;
    public static int GL_TEXTURE_2D = 3553;
    public static int GL_FEEDBACK_BUFFER_POINTER = 3568;
    public static int GL_FEEDBACK_BUFFER_SIZE = 3569;
    public static int GL_FEEDBACK_BUFFER_TYPE = 3570;
    public static int GL_SELECTION_BUFFER_POINTER = 3571;
    public static int GL_SELECTION_BUFFER_SIZE = 3572;
    public static int GL_TEXTURE_WIDTH = 4096;
    public static int GL_TEXTURE_HEIGHT = 4097;
    public static int GL_TEXTURE_INTERNAL_FORMAT = 4099;
    public static int GL_TEXTURE_BORDER_COLOR = 4100;
    public static int GL_TEXTURE_BORDER = 4101;
    public static int GL_DONT_CARE = 4352;
    public static int GL_FASTEST = 4353;
    public static int GL_NICEST = 4354;
    public static int GL_LIGHT0 = 16384;
    public static int GL_LIGHT1 = 16385;
    public static int GL_LIGHT2 = 16386;
    public static int GL_LIGHT3 = 16387;
    public static int GL_LIGHT4 = 16388;
    public static int GL_LIGHT5 = 16389;
    public static int GL_LIGHT6 = 16390;
    public static int GL_LIGHT7 = 16391;
    public static int GL_AMBIENT = 4608;
    public static int GL_DIFFUSE = 4609;
    public static int GL_SPECULAR = 4610;
    public static int GL_POSITION = 4611;
    public static int GL_SPOT_DIRECTION = 4612;
    public static int GL_SPOT_EXPONENT = 4613;
    public static int GL_SPOT_CUTOFF = 4614;
    public static int GL_CONSTANT_ATTENUATION = 4615;
    public static int GL_LINEAR_ATTENUATION = 4616;
    public static int GL_QUADRATIC_ATTENUATION = 4617;
    public static int GL_COMPILE = 4864;
    public static int GL_COMPILE_AND_EXECUTE = 4865;
    public static int GL_CLEAR = 5376;
    public static int GL_AND = 5377;
    public static int GL_AND_REVERSE = 5378;
    public static int GL_COPY = 5379;
    public static int GL_AND_INVERTED = 5380;
    public static int GL_NOOP = 5381;
    public static int GL_XOR = 5382;
    public static int GL_OR = 5383;
    public static int GL_NOR = 5384;
    public static int GL_EQUIV = 5385;
    public static int GL_INVERT = 5386;
    public static int GL_OR_REVERSE = 5387;
    public static int GL_COPY_INVERTED = 5388;
    public static int GL_OR_INVERTED = 5389;
    public static int GL_NAND = 5390;
    public static int GL_SET = 5391;
    public static int GL_EMISSION = 5632;
    public static int GL_SHININESS = 5633;
    public static int GL_AMBIENT_AND_DIFFUSE = 5634;
    public static int GL_COLOR_INDEXES = 5635;
    public static int GL_MODELVIEW = 5888;
    public static int GL_PROJECTION = 5889;
    public static int GL_TEXTURE = 5890;
    public static int GL_COLOR = 6144;
    public static int GL_DEPTH = 6145;
    public static int GL_STENCIL = 6146;
    public static int GL_COLOR_INDEX = 6400;
    public static int GL_STENCIL_INDEX = 6401;
    public static int GL_DEPTH_COMPONENT = 6402;
    public static int GL_RED = 6403;
    public static int GL_GREEN = 6404;
    public static int GL_BLUE = 6405;
    public static int GL_ALPHA = 6406;
    public static int GL_RGB = 6407;
    public static int GL_RGBA = 6408;
    public static int GL_LUMINANCE = 6409;
    public static int GL_LUMINANCE_ALPHA = 6410;
    public static int GL_BITMAP = 6656;
    public static int GL_POINT = 6912;
    public static int GL_LINE = 6913;
    public static int GL_FILL = 6914;
    public static int GL_RENDER = 7168;
    public static int GL_FEEDBACK = 7169;
    public static int GL_SELECT = 7170;
    public static int GL_FLAT = 7424;
    public static int GL_SMOOTH = 7425;
    public static int GL_KEEP = 7680;
    public static int GL_REPLACE = 7681;
    public static int GL_INCR = 7682;
    public static int GL_DECR = 7683;
    public static int GL_VENDOR = 7936;
    public static int GL_RENDERER = 7937;
    public static int GL_VERSION = 7938;
    public static int GL_EXTENSIONS = 7939;
    public static int GL_S = 8192;
    public static int GL_T = 8193;
    public static int GL_R = 8194;
    public static int GL_Q = 8195;
    public static int GL_MODULATE = 8448;
    public static int GL_DECAL = 8449;
    public static int GL_TEXTURE_ENV_MODE = 8704;
    public static int GL_TEXTURE_ENV_COLOR = 8705;
    public static int GL_TEXTURE_ENV = 8960;
    public static int GL_EYE_LINEAR = 9216;
    public static int GL_OBJECT_LINEAR = 9217;
    public static int GL_SPHERE_MAP = 9218;
    public static int GL_TEXTURE_GEN_MODE = 9472;
    public static int GL_OBJECT_PLANE = 9473;
    public static int GL_EYE_PLANE = 9474;
    public static int GL_NEAREST = 9728;
    public static int GL_LINEAR = 9729;
    public static int GL_NEAREST_MIPMAP_NEAREST = 9984;
    public static int GL_LINEAR_MIPMAP_NEAREST = 9985;
    public static int GL_NEAREST_MIPMAP_LINEAR = 9986;
    public static int GL_LINEAR_MIPMAP_LINEAR = 9987;
    public static int GL_TEXTURE_MAG_FILTER = 10240;
    public static int GL_TEXTURE_MIN_FILTER = 10241;
    public static int GL_TEXTURE_WRAP_S = 10242;
    public static int GL_TEXTURE_WRAP_T = 10243;
    public static int GL_CLAMP = 10496;
    public static int GL_REPEAT = 10497;
    public static int GL_CLIENT_PIXEL_STORE_BIT = 1;
    public static int GL_CLIENT_VERTEX_ARRAY_BIT = 2;
    public static int GL_ALL_CLIENT_ATTRIB_BITS = -1;
    public static int GL_POLYGON_OFFSET_FACTOR = 32824;
    public static int GL_POLYGON_OFFSET_UNITS = 10752;
    public static int GL_POLYGON_OFFSET_POINT = 10753;
    public static int GL_POLYGON_OFFSET_LINE = 10754;
    public static int GL_POLYGON_OFFSET_FILL = 32823;
    public static int GL_ALPHA4 = 32827;
    public static int GL_ALPHA8 = 32828;
    public static int GL_ALPHA12 = 32829;
    public static int GL_ALPHA16 = 32830;
    public static int GL_LUMINANCE4 = 32831;
    public static int GL_LUMINANCE8 = 32832;
    public static int GL_LUMINANCE12 = 32833;
    public static int GL_LUMINANCE16 = 32834;
    public static int GL_LUMINANCE4_ALPHA4 = 32835;
    public static int GL_LUMINANCE6_ALPHA2 = 32836;
    public static int GL_LUMINANCE8_ALPHA8 = 32837;
    public static int GL_LUMINANCE12_ALPHA4 = 32838;
    public static int GL_LUMINANCE12_ALPHA12 = 32839;
    public static int GL_LUMINANCE16_ALPHA16 = 32840;
    public static int GL_INTENSITY = 32841;
    public static int GL_INTENSITY4 = 32842;
    public static int GL_INTENSITY8 = 32843;
    public static int GL_INTENSITY12 = 32844;
    public static int GL_INTENSITY16 = 32845;
    public static int GL_R3_G3_B2 = 10768;
    public static int GL_RGB4 = 32847;
    public static int GL_RGB5 = 32848;
    public static int GL_RGB8 = 32849;
    public static int GL_RGB10 = 32850;
    public static int GL_RGB12 = 32851;
    public static int GL_RGB16 = 32852;
    public static int GL_RGBA2 = 32853;
    public static int GL_RGBA4 = 32854;
    public static int GL_RGB5_A1 = 32855;
    public static int GL_RGBA8 = 32856;
    public static int GL_RGB10_A2 = 32857;
    public static int GL_RGBA12 = 32858;
    public static int GL_RGBA16 = 32859;
    public static int GL_TEXTURE_RED_SIZE = 32860;
    public static int GL_TEXTURE_GREEN_SIZE = 32861;
    public static int GL_TEXTURE_BLUE_SIZE = 32862;
    public static int GL_TEXTURE_ALPHA_SIZE = 32863;
    public static int GL_TEXTURE_LUMINANCE_SIZE = 32864;
    public static int GL_TEXTURE_INTENSITY_SIZE = 32865;
    public static int GL_PROXY_TEXTURE_1D = 32867;
    public static int GL_PROXY_TEXTURE_2D = 32868;
    public static int GL_TEXTURE_PRIORITY = 32870;
    public static int GL_TEXTURE_RESIDENT = 32871;
    public static int GL_TEXTURE_BINDING_1D = 32872;
    public static int GL_TEXTURE_BINDING_2D = 32873;
    public static int GL_VERTEX_ARRAY = 32884;
    public static int GL_NORMAL_ARRAY = 32885;
    public static int GL_COLOR_ARRAY = 32886;
    public static int GL_INDEX_ARRAY = 32887;
    public static int GL_TEXTURE_COORD_ARRAY = 32888;
    public static int GL_EDGE_FLAG_ARRAY = 32889;
    public static int GL_VERTEX_ARRAY_SIZE = 32890;
    public static int GL_VERTEX_ARRAY_TYPE = 32891;
    public static int GL_VERTEX_ARRAY_STRIDE = 32892;
    public static int GL_NORMAL_ARRAY_TYPE = 32894;
    public static int GL_NORMAL_ARRAY_STRIDE = 32895;
    public static int GL_COLOR_ARRAY_SIZE = 32897;
    public static int GL_COLOR_ARRAY_TYPE = 32898;
    public static int GL_COLOR_ARRAY_STRIDE = 32899;
    public static int GL_INDEX_ARRAY_TYPE = 32901;
    public static int GL_INDEX_ARRAY_STRIDE = 32902;
    public static int GL_TEXTURE_COORD_ARRAY_SIZE = 32904;
    public static int GL_TEXTURE_COORD_ARRAY_TYPE = 32905;
    public static int GL_TEXTURE_COORD_ARRAY_STRIDE = 32906;
    public static int GL_EDGE_FLAG_ARRAY_STRIDE = 32908;
    public static int GL_VERTEX_ARRAY_POINTER = 32910;
    public static int GL_NORMAL_ARRAY_POINTER = 32911;
    public static int GL_COLOR_ARRAY_POINTER = 32912;
    public static int GL_INDEX_ARRAY_POINTER = 32913;
    public static int GL_TEXTURE_COORD_ARRAY_POINTER = 32914;
    public static int GL_EDGE_FLAG_ARRAY_POINTER = 32915;
    public static int GL_V2F = 10784;
    public static int GL_V3F = 10785;
    public static int GL_C4UB_V2F = 10786;
    public static int GL_C4UB_V3F = 10787;
    public static int GL_C3F_V3F = 10788;
    public static int GL_N3F_V3F = 10789;
    public static int GL_C4F_N3F_V3F = 10790;
    public static int GL_T2F_V3F = 10791;
    public static int GL_T4F_V4F = 10792;
    public static int GL_T2F_C4UB_V3F = 10793;
    public static int GL_T2F_C3F_V3F = 10794;
    public static int GL_T2F_N3F_V3F = 10795;
    public static int GL_T2F_C4F_N3F_V3F = 10796;
    public static int GL_T4F_C4F_N3F_V4F = 10797;
    public static int GL_LOGIC_OP = 3057;
    public static int GL_TEXTURE_COMPONENTS = 4099;
    public static int GL_TEXTURE_BINDING_3D = 32874;
    public static int GL_PACK_SKIP_IMAGES = 32875;
    public static int GL_PACK_IMAGE_HEIGHT = 32876;
    public static int GL_UNPACK_SKIP_IMAGES = 32877;
    public static int GL_UNPACK_IMAGE_HEIGHT = 32878;
    public static int GL_TEXTURE_3D = 32879;
    public static int GL_PROXY_TEXTURE_3D = 32880;
    public static int GL_TEXTURE_DEPTH = 32881;
    public static int GL_TEXTURE_WRAP_R = 32882;
    public static int GL_MAX_3D_TEXTURE_SIZE = 32883;
    public static int GL_BGR = 32992;
    public static int GL_BGRA = 32993;
    public static int GL_UNSIGNED_BYTE_3_3_2 = 32818;
    public static int GL_UNSIGNED_BYTE_2_3_3_REV = 33634;
    public static int GL_UNSIGNED_SHORT_5_6_5 = 33635;
    public static int GL_UNSIGNED_SHORT_5_6_5_REV = 33636;
    public static int GL_UNSIGNED_SHORT_4_4_4_4 = 32819;
    public static int GL_UNSIGNED_SHORT_4_4_4_4_REV = 33637;
    public static int GL_UNSIGNED_SHORT_5_5_5_1 = 32820;
    public static int GL_UNSIGNED_SHORT_1_5_5_5_REV = 33638;
    public static int GL_UNSIGNED_INT_8_8_8_8 = 32821;
    public static int GL_UNSIGNED_INT_8_8_8_8_REV = 33639;
    public static int GL_UNSIGNED_INT_10_10_10_2 = 32822;
    public static int GL_UNSIGNED_INT_2_10_10_10_REV = 33640;
    public static int GL_RESCALE_NORMAL = 32826;
    public static int GL_LIGHT_MODEL_COLOR_CONTROL = 33272;
    public static int GL_SINGLE_COLOR = 33273;
    public static int GL_SEPARATE_SPECULAR_COLOR = 33274;
    public static int GL_CLAMP_TO_EDGE = 33071;
    public static int GL_TEXTURE_MIN_LOD = 33082;
    public static int GL_TEXTURE_MAX_LOD = 33083;
    public static int GL_TEXTURE_BASE_LEVEL = 33084;
    public static int GL_TEXTURE_MAX_LEVEL = 33085;
    public static int GL_MAX_ELEMENTS_VERTICES = 33000;
    public static int GL_MAX_ELEMENTS_INDICES = 33001;
    public static int GL_ALIASED_POINT_SIZE_RANGE = 33901;
    public static int GL_ALIASED_LINE_WIDTH_RANGE = 33902;
    public static int GL_SMOOTH_POINT_SIZE_RANGE = 2834;
    public static int GL_SMOOTH_POINT_SIZE_GRANULARITY = 2835;
    public static int GL_SMOOTH_LINE_WIDTH_RANGE = 2850;
    public static int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 2851;
    public static int GL_TEXTURE0 = 33984;
    public static int GL_TEXTURE1 = 33985;
    public static int GL_TEXTURE2 = 33986;
    public static int GL_TEXTURE3 = 33987;
    public static int GL_TEXTURE4 = 33988;
    public static int GL_TEXTURE5 = 33989;
    public static int GL_TEXTURE6 = 33990;
    public static int GL_TEXTURE7 = 33991;
    public static int GL_TEXTURE8 = 33992;
    public static int GL_TEXTURE9 = 33993;
    public static int GL_TEXTURE10 = 33994;
    public static int GL_TEXTURE11 = 33995;
    public static int GL_TEXTURE12 = 33996;
    public static int GL_TEXTURE13 = 33997;
    public static int GL_TEXTURE14 = 33998;
    public static int GL_TEXTURE15 = 33999;
    public static int GL_TEXTURE16 = 34000;
    public static int GL_TEXTURE17 = 34001;
    public static int GL_TEXTURE18 = 34002;
    public static int GL_TEXTURE19 = 34003;
    public static int GL_TEXTURE20 = 34004;
    public static int GL_TEXTURE21 = 34005;
    public static int GL_TEXTURE22 = 34006;
    public static int GL_TEXTURE23 = 34007;
    public static int GL_TEXTURE24 = 34008;
    public static int GL_TEXTURE25 = 34009;
    public static int GL_TEXTURE26 = 34010;
    public static int GL_TEXTURE27 = 34011;
    public static int GL_TEXTURE28 = 34012;
    public static int GL_TEXTURE29 = 34013;
    public static int GL_TEXTURE30 = 34014;
    public static int GL_TEXTURE31 = 34015;
    public static int GL_ACTIVE_TEXTURE = 34016;
    public static int GL_CLIENT_ACTIVE_TEXTURE = 34017;
    public static int GL_MAX_TEXTURE_UNITS = 34018;
    public static int GL_NORMAL_MAP = 34065;
    public static int GL_REFLECTION_MAP = 34066;
    public static int GL_TEXTURE_CUBE_MAP = 34067;
    public static int GL_TEXTURE_BINDING_CUBE_MAP = 34068;
    public static int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 34069;
    public static int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 34070;
    public static int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 34071;
    public static int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 34072;
    public static int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 34073;
    public static int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 34074;
    public static int GL_PROXY_TEXTURE_CUBE_MAP = 34075;
    public static int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 34076;
    public static int GL_COMPRESSED_ALPHA = 34025;
    public static int GL_COMPRESSED_LUMINANCE = 34026;
    public static int GL_COMPRESSED_LUMINANCE_ALPHA = 34027;
    public static int GL_COMPRESSED_INTENSITY = 34028;
    public static int GL_COMPRESSED_RGB = 34029;
    public static int GL_COMPRESSED_RGBA = 34030;
    public static int GL_TEXTURE_COMPRESSION_HINT = 34031;
    public static int GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 34464;
    public static int GL_TEXTURE_COMPRESSED = 34465;
    public static int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 34466;
    public static int GL_COMPRESSED_TEXTURE_FORMATS = 34467;
    public static int GL_MULTISAMPLE = 32925;
    public static int GL_SAMPLE_ALPHA_TO_COVERAGE = 32926;
    public static int GL_SAMPLE_ALPHA_TO_ONE = 32927;
    public static int GL_SAMPLE_COVERAGE = 32928;
    public static int GL_SAMPLE_BUFFERS = 32936;
    public static int GL_SAMPLES = 32937;
    public static int GL_SAMPLE_COVERAGE_VALUE = 32938;
    public static int GL_SAMPLE_COVERAGE_INVERT = 32939;
    public static int GL_MULTISAMPLE_BIT = 536870912;
    public static int GL_TRANSPOSE_MODELVIEW_MATRIX = 34019;
    public static int GL_TRANSPOSE_PROJECTION_MATRIX = 34020;
    public static int GL_TRANSPOSE_TEXTURE_MATRIX = 34021;
    public static int GL_TRANSPOSE_COLOR_MATRIX = 34022;
    public static int GL_COMBINE = 34160;
    public static int GL_COMBINE_RGB = 34161;
    public static int GL_COMBINE_ALPHA = 34162;
    public static int GL_SOURCE0_RGB = 34176;
    public static int GL_SOURCE1_RGB = 34177;
    public static int GL_SOURCE2_RGB = 34178;
    public static int GL_SOURCE0_ALPHA = 34184;
    public static int GL_SOURCE1_ALPHA = 34185;
    public static int GL_SOURCE2_ALPHA = 34186;
    public static int GL_OPERAND0_RGB = 34192;
    public static int GL_OPERAND1_RGB = 34193;
    public static int GL_OPERAND2_RGB = 34194;
    public static int GL_OPERAND0_ALPHA = 34200;
    public static int GL_OPERAND1_ALPHA = 34201;
    public static int GL_OPERAND2_ALPHA = 34202;
    public static int GL_RGB_SCALE = 34163;
    public static int GL_ADD_SIGNED = 34164;
    public static int GL_INTERPOLATE = 34165;
    public static int GL_SUBTRACT = 34023;
    public static int GL_CONSTANT = 34166;
    public static int GL_PRIMARY_COLOR = 34167;
    public static int GL_PREVIOUS = 34168;
    public static int GL_DOT3_RGB = 34478;
    public static int GL_DOT3_RGBA = 34479;
    public static int GL_CLAMP_TO_BORDER = 33069;
    public static int GL_GENERATE_MIPMAP = 33169;
    public static int GL_GENERATE_MIPMAP_HINT = 33170;
    public static int GL_DEPTH_COMPONENT16 = 33189;
    public static int GL_DEPTH_COMPONENT24 = 33190;
    public static int GL_DEPTH_COMPONENT32 = 33191;
    public static int GL_TEXTURE_DEPTH_SIZE = 34890;
    public static int GL_DEPTH_TEXTURE_MODE = 34891;
    public static int GL_TEXTURE_COMPARE_MODE = 34892;
    public static int GL_TEXTURE_COMPARE_FUNC = 34893;
    public static int GL_COMPARE_R_TO_TEXTURE = 34894;
    public static int GL_FOG_COORDINATE_SOURCE = 33872;
    public static int GL_FOG_COORDINATE = 33873;
    public static int GL_FRAGMENT_DEPTH = 33874;
    public static int GL_CURRENT_FOG_COORDINATE = 33875;
    public static int GL_FOG_COORDINATE_ARRAY_TYPE = 33876;
    public static int GL_FOG_COORDINATE_ARRAY_STRIDE = 33877;
    public static int GL_FOG_COORDINATE_ARRAY_POINTER = 33878;
    public static int GL_FOG_COORDINATE_ARRAY = 33879;
    public static int GL_POINT_SIZE_MIN = 33062;
    public static int GL_POINT_SIZE_MAX = 33063;
    public static int GL_POINT_FADE_THRESHOLD_SIZE = 33064;
    public static int GL_POINT_DISTANCE_ATTENUATION = 33065;
    public static int GL_COLOR_SUM = 33880;
    public static int GL_CURRENT_SECONDARY_COLOR = 33881;
    public static int GL_SECONDARY_COLOR_ARRAY_SIZE = 33882;
    public static int GL_SECONDARY_COLOR_ARRAY_TYPE = 33883;
    public static int GL_SECONDARY_COLOR_ARRAY_STRIDE = 33884;
    public static int GL_SECONDARY_COLOR_ARRAY_POINTER = 33885;
    public static int GL_SECONDARY_COLOR_ARRAY = 33886;
    public static int GL_BLEND_DST_RGB = 32968;
    public static int GL_BLEND_SRC_RGB = 32969;
    public static int GL_BLEND_DST_ALPHA = 32970;
    public static int GL_BLEND_SRC_ALPHA = 32971;
    public static int GL_INCR_WRAP = 34055;
    public static int GL_DECR_WRAP = 34056;
    public static int GL_TEXTURE_FILTER_CONTROL = 34048;
    public static int GL_TEXTURE_LOD_BIAS = 34049;
    public static int GL_MAX_TEXTURE_LOD_BIAS = 34045;
    public static int GL_MIRRORED_REPEAT = 33648;
    public static int GL_BLEND_COLOR = 32773;
    public static int GL_BLEND_EQUATION = 32777;
    public static int GL_FUNC_ADD = 32774;
    public static int GL_FUNC_SUBTRACT = 32778;
    public static int GL_FUNC_REVERSE_SUBTRACT = 32779;
    public static int GL_MIN = 32775;
    public static int GL_MAX = 32776;
    public static int GL_ARRAY_BUFFER = 34962;
    public static int GL_ELEMENT_ARRAY_BUFFER = 34963;
    public static int GL_ARRAY_BUFFER_BINDING = 34964;
    public static int GL_ELEMENT_ARRAY_BUFFER_BINDING = 34965;
    public static int GL_VERTEX_ARRAY_BUFFER_BINDING = 34966;
    public static int GL_NORMAL_ARRAY_BUFFER_BINDING = 34967;
    public static int GL_COLOR_ARRAY_BUFFER_BINDING = 34968;
    public static int GL_INDEX_ARRAY_BUFFER_BINDING = 34969;
    public static int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 34970;
    public static int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 34971;
    public static int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 34972;
    public static int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 34973;
    public static int GL_WEIGHT_ARRAY_BUFFER_BINDING = 34974;
    public static int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 34975;
    public static int GL_STREAM_DRAW = 35040;
    public static int GL_STREAM_READ = 35041;
    public static int GL_STREAM_COPY = 35042;
    public static int GL_STATIC_DRAW = 35044;
    public static int GL_STATIC_READ = 35045;
    public static int GL_STATIC_COPY = 35046;
    public static int GL_DYNAMIC_DRAW = 35048;
    public static int GL_DYNAMIC_READ = 35049;
    public static int GL_DYNAMIC_COPY = 35050;
    public static int GL_READ_ONLY = 35000;
    public static int GL_WRITE_ONLY = 35001;
    public static int GL_READ_WRITE = 35002;
    public static int GL_BUFFER_SIZE = 34660;
    public static int GL_BUFFER_USAGE = 34661;
    public static int GL_BUFFER_ACCESS = 35003;
    public static int GL_BUFFER_MAPPED = 35004;
    public static int GL_BUFFER_MAP_POINTER = 35005;
    public static int GL_FOG_COORD_SRC = 33872;
    public static int GL_FOG_COORD = 33873;
    public static int GL_CURRENT_FOG_COORD = 33875;
    public static int GL_FOG_COORD_ARRAY_TYPE = 33876;
    public static int GL_FOG_COORD_ARRAY_STRIDE = 33877;
    public static int GL_FOG_COORD_ARRAY_POINTER = 33878;
    public static int GL_FOG_COORD_ARRAY = 33879;
    public static int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 34973;
    public static int GL_SRC0_RGB = 34176;
    public static int GL_SRC1_RGB = 34177;
    public static int GL_SRC2_RGB = 34178;
    public static int GL_SRC0_ALPHA = 34184;
    public static int GL_SRC1_ALPHA = 34185;
    public static int GL_SRC2_ALPHA = 34186;
    public static int GL_SAMPLES_PASSED = 35092;
    public static int GL_QUERY_COUNTER_BITS = 34916;
    public static int GL_CURRENT_QUERY = 34917;
    public static int GL_QUERY_RESULT = 34918;
    public static int GL_QUERY_RESULT_AVAILABLE = 34919;
    public static int GL_SHADING_LANGUAGE_VERSION = 35724;
    public static int GL_CURRENT_PROGRAM = 35725;
    public static int GL_SHADER_TYPE = 35663;
    public static int GL_DELETE_STATUS = 35712;
    public static int GL_COMPILE_STATUS = 35713;
    public static int GL_LINK_STATUS = 35714;
    public static int GL_VALIDATE_STATUS = 35715;
    public static int GL_INFO_LOG_LENGTH = 35716;
    public static int GL_ATTACHED_SHADERS = 35717;
    public static int GL_ACTIVE_UNIFORMS = 35718;
    public static int GL_ACTIVE_UNIFORM_MAX_LENGTH = 35719;
    public static int GL_ACTIVE_ATTRIBUTES = 35721;
    public static int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 35722;
    public static int GL_SHADER_SOURCE_LENGTH = 35720;
    public static int GL_SHADER_OBJECT = 35656;
    public static int GL_FLOAT_VEC2 = 35664;
    public static int GL_FLOAT_VEC3 = 35665;
    public static int GL_FLOAT_VEC4 = 35666;
    public static int GL_INT_VEC2 = 35667;
    public static int GL_INT_VEC3 = 35668;
    public static int GL_INT_VEC4 = 35669;
    public static int GL_BOOL = 35670;
    public static int GL_BOOL_VEC2 = 35671;
    public static int GL_BOOL_VEC3 = 35672;
    public static int GL_BOOL_VEC4 = 35673;
    public static int GL_FLOAT_MAT2 = 35674;
    public static int GL_FLOAT_MAT3 = 35675;
    public static int GL_FLOAT_MAT4 = 35676;
    public static int GL_SAMPLER_1D = 35677;
    public static int GL_SAMPLER_2D = 35678;
    public static int GL_SAMPLER_3D = 35679;
    public static int GL_SAMPLER_CUBE = 35680;
    public static int GL_SAMPLER_1D_SHADOW = 35681;
    public static int GL_SAMPLER_2D_SHADOW = 35682;
    public static int GL_VERTEX_SHADER = 35633;
    public static int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 35658;
    public static int GL_MAX_VARYING_FLOATS = 35659;
    public static int GL_MAX_VERTEX_ATTRIBS = 34921;
    public static int GL_MAX_TEXTURE_IMAGE_UNITS = 34930;
    public static int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 35660;
    public static int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 35661;
    public static int GL_MAX_TEXTURE_COORDS = 34929;
    public static int GL_VERTEX_PROGRAM_POINT_SIZE = 34370;
    public static int GL_VERTEX_PROGRAM_TWO_SIDE = 34371;
    public static int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 34338;
    public static int GL_VERTEX_ATTRIB_ARRAY_SIZE = 34339;
    public static int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 34340;
    public static int GL_VERTEX_ATTRIB_ARRAY_TYPE = 34341;
    public static int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 34922;
    public static int GL_CURRENT_VERTEX_ATTRIB = 34342;
    public static int GL_VERTEX_ATTRIB_ARRAY_POINTER = 34373;
    public static int GL_FRAGMENT_SHADER = 35632;
    public static int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 35657;
    public static int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 35723;
    public static int GL_MAX_DRAW_BUFFERS = 34852;
    public static int GL_DRAW_BUFFER0 = 34853;
    public static int GL_DRAW_BUFFER1 = 34854;
    public static int GL_DRAW_BUFFER2 = 34855;
    public static int GL_DRAW_BUFFER3 = 34856;
    public static int GL_DRAW_BUFFER4 = 34857;
    public static int GL_DRAW_BUFFER5 = 34858;
    public static int GL_DRAW_BUFFER6 = 34859;
    public static int GL_DRAW_BUFFER7 = 34860;
    public static int GL_DRAW_BUFFER8 = 34861;
    public static int GL_DRAW_BUFFER9 = 34862;
    public static int GL_DRAW_BUFFER10 = 34863;
    public static int GL_DRAW_BUFFER11 = 34864;
    public static int GL_DRAW_BUFFER12 = 34865;
    public static int GL_DRAW_BUFFER13 = 34866;
    public static int GL_DRAW_BUFFER14 = 34867;
    public static int GL_DRAW_BUFFER15 = 34868;
    public static int GL_POINT_SPRITE = 34913;
    public static int GL_COORD_REPLACE = 34914;
    public static int GL_POINT_SPRITE_COORD_ORIGIN = 36000;
    public static int GL_LOWER_LEFT = 36001;
    public static int GL_UPPER_LEFT = 36002;
    public static int GL_STENCIL_BACK_FUNC = 34816;
    public static int GL_STENCIL_BACK_FAIL = 34817;
    public static int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 34818;
    public static int GL_STENCIL_BACK_PASS_DEPTH_PASS = 34819;
    public static int GL_STENCIL_BACK_REF = 36003;
    public static int GL_STENCIL_BACK_VALUE_MASK = 36004;
    public static int GL_STENCIL_BACK_WRITEMASK = 36005;
    public static int GL_BLEND_EQUATION_RGB = 32777;
    public static int GL_BLEND_EQUATION_ALPHA = 34877;
    public static int GL_FLOAT_MAT2x3 = 35685;
    public static int GL_FLOAT_MAT2x4 = 35686;
    public static int GL_FLOAT_MAT3x2 = 35687;
    public static int GL_FLOAT_MAT3x4 = 35688;
    public static int GL_FLOAT_MAT4x2 = 35689;
    public static int GL_FLOAT_MAT4x3 = 35690;
    public static int GL_PIXEL_PACK_BUFFER = 35051;
    public static int GL_PIXEL_UNPACK_BUFFER = 35052;
    public static int GL_PIXEL_PACK_BUFFER_BINDING = 35053;
    public static int GL_PIXEL_UNPACK_BUFFER_BINDING = 35055;
    public static int GL_SRGB = 35904;
    public static int GL_SRGB8 = 35905;
    public static int GL_SRGB_ALPHA = 35906;
    public static int GL_SRGB8_ALPHA8 = 35907;
    public static int GL_SLUMINANCE_ALPHA = 35908;
    public static int GL_SLUMINANCE8_ALPHA8 = 35909;
    public static int GL_SLUMINANCE = 35910;
    public static int GL_SLUMINANCE8 = 35911;
    public static int GL_COMPRESSED_SRGB = 35912;
    public static int GL_COMPRESSED_SRGB_ALPHA = 35913;
    public static int GL_COMPRESSED_SLUMINANCE = 35914;
    public static int GL_COMPRESSED_SLUMINANCE_ALPHA = 35915;
    public static int GL_CURRENT_RASTER_SECONDARY_COLOR = 33887;
    public static int GL_MAJOR_VERSION = 33307;
    public static int GL_MINOR_VERSION = 33308;
    public static int GL_NUM_EXTENSIONS = 33309;
    public static int GL_CONTEXT_FLAGS = 33310;
    public static int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 1;
    public static int GL_DEPTH_BUFFER = 33315;
    public static int GL_STENCIL_BUFFER = 33316;
    public static int GL_COMPRESSED_RED = 33317;
    public static int GL_COMPRESSED_RG = 33318;
    public static int GL_COMPARE_REF_TO_TEXTURE = 34894;
    public static int GL_CLIP_DISTANCE0 = 12288;
    public static int GL_CLIP_DISTANCE1 = 12289;
    public static int GL_CLIP_DISTANCE2 = 12290;
    public static int GL_CLIP_DISTANCE3 = 12291;
    public static int GL_CLIP_DISTANCE4 = 12292;
    public static int GL_CLIP_DISTANCE5 = 12293;
    public static int GL_CLIP_DISTANCE6 = 12294;
    public static int GL_CLIP_DISTANCE7 = 12295;
    public static int GL_MAX_CLIP_DISTANCES = 3378;
    public static int GL_MAX_VARYING_COMPONENTS = 35659;
    public static int GL_BUFFER_ACCESS_FLAGS = 37151;
    public static int GL_BUFFER_MAP_LENGTH = 37152;
    public static int GL_BUFFER_MAP_OFFSET = 37153;
    public static int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 35069;
    public static int GL_SAMPLER_BUFFER = 36290;
    public static int GL_SAMPLER_CUBE_SHADOW = 36293;
    public static int GL_UNSIGNED_INT_VEC2 = 36294;
    public static int GL_UNSIGNED_INT_VEC3 = 36295;
    public static int GL_UNSIGNED_INT_VEC4 = 36296;
    public static int GL_INT_SAMPLER_1D = 36297;
    public static int GL_INT_SAMPLER_2D = 36298;
    public static int GL_INT_SAMPLER_3D = 36299;
    public static int GL_INT_SAMPLER_CUBE = 36300;
    public static int GL_INT_SAMPLER_2D_RECT = 36301;
    public static int GL_INT_SAMPLER_1D_ARRAY = 36302;
    public static int GL_INT_SAMPLER_2D_ARRAY = 36303;
    public static int GL_INT_SAMPLER_BUFFER = 36304;
    public static int GL_UNSIGNED_INT_SAMPLER_1D = 36305;
    public static int GL_UNSIGNED_INT_SAMPLER_2D = 36306;
    public static int GL_UNSIGNED_INT_SAMPLER_3D = 36307;
    public static int GL_UNSIGNED_INT_SAMPLER_CUBE = 36308;
    public static int GL_UNSIGNED_INT_SAMPLER_2D_RECT = 36309;
    public static int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 36310;
    public static int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 36311;
    public static int GL_UNSIGNED_INT_SAMPLER_BUFFER = 36312;
    public static int GL_MIN_PROGRAM_TEXEL_OFFSET = 35076;
    public static int GL_MAX_PROGRAM_TEXEL_OFFSET = 35077;
    public static int GL_QUERY_WAIT = 36371;
    public static int GL_QUERY_NO_WAIT = 36372;
    public static int GL_QUERY_BY_REGION_WAIT = 36373;
    public static int GL_QUERY_BY_REGION_NO_WAIT = 36374;
    public static int GL_MAP_READ_BIT = 1;
    public static int GL_MAP_WRITE_BIT = 2;
    public static int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    public static int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    public static int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    public static int GL_MAP_UNSYNCHRONIZED_BIT = 32;
    public static int GL_CLAMP_VERTEX_COLOR = 35098;
    public static int GL_CLAMP_FRAGMENT_COLOR = 35099;
    public static int GL_CLAMP_READ_COLOR = 35100;
    public static int GL_FIXED_ONLY = 35101;
    public static int GL_DEPTH_COMPONENT32F = 36012;
    public static int GL_DEPTH32F_STENCIL8 = 36013;
    public static int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 36269;
    public static int GL_TEXTURE_RED_TYPE = 35856;
    public static int GL_TEXTURE_GREEN_TYPE = 35857;
    public static int GL_TEXTURE_BLUE_TYPE = 35858;
    public static int GL_TEXTURE_ALPHA_TYPE = 35859;
    public static int GL_TEXTURE_LUMINANCE_TYPE = 35860;
    public static int GL_TEXTURE_INTENSITY_TYPE = 35861;
    public static int GL_TEXTURE_DEPTH_TYPE = 35862;
    public static int GL_UNSIGNED_NORMALIZED = 35863;
    public static int GL_RGBA32F = 34836;
    public static int GL_RGB32F = 34837;
    public static int GL_ALPHA32F = 34838;
    public static int GL_RGBA16F = 34842;
    public static int GL_RGB16F = 34843;
    public static int GL_ALPHA16F = 34844;
    public static int GL_R11F_G11F_B10F = 35898;
    public static int GL_UNSIGNED_INT_10F_11F_11F_REV = 35899;
    public static int GL_RGB9_E5 = 35901;
    public static int GL_UNSIGNED_INT_5_9_9_9_REV = 35902;
    public static int GL_TEXTURE_SHARED_SIZE = 35903;
    public static int GL_FRAMEBUFFER = 36160;
    public static int GL_READ_FRAMEBUFFER = 36008;
    public static int GL_DRAW_FRAMEBUFFER = 36009;
    public static int GL_RENDERBUFFER = 36161;
    public static int GL_STENCIL_INDEX1 = 36166;
    public static int GL_STENCIL_INDEX4 = 36167;
    public static int GL_STENCIL_INDEX8 = 36168;
    public static int GL_STENCIL_INDEX16 = 36169;
    public static int GL_RENDERBUFFER_WIDTH = 36162;
    public static int GL_RENDERBUFFER_HEIGHT = 36163;
    public static int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
    public static int GL_RENDERBUFFER_RED_SIZE = 36176;
    public static int GL_RENDERBUFFER_GREEN_SIZE = 36177;
    public static int GL_RENDERBUFFER_BLUE_SIZE = 36178;
    public static int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
    public static int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
    public static int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
    public static int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
    public static int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
    public static int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
    public static int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
    public static int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    public static int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
    public static int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
    public static int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
    public static int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
    public static int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
    public static int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
    public static int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
    public static int GL_FRAMEBUFFER_DEFAULT = 33304;
    public static int GL_INDEX = 33314;
    public static int GL_COLOR_ATTACHMENT0 = 36064;
    public static int GL_COLOR_ATTACHMENT1 = 36065;
    public static int GL_COLOR_ATTACHMENT2 = 36066;
    public static int GL_COLOR_ATTACHMENT3 = 36067;
    public static int GL_COLOR_ATTACHMENT4 = 36068;
    public static int GL_COLOR_ATTACHMENT5 = 36069;
    public static int GL_COLOR_ATTACHMENT6 = 36070;
    public static int GL_COLOR_ATTACHMENT7 = 36071;
    public static int GL_COLOR_ATTACHMENT8 = 36072;
    public static int GL_COLOR_ATTACHMENT9 = 36073;
    public static int GL_COLOR_ATTACHMENT10 = 36074;
    public static int GL_COLOR_ATTACHMENT11 = 36075;
    public static int GL_COLOR_ATTACHMENT12 = 36076;
    public static int GL_COLOR_ATTACHMENT13 = 36077;
    public static int GL_COLOR_ATTACHMENT14 = 36078;
    public static int GL_COLOR_ATTACHMENT15 = 36079;
    public static int GL_DEPTH_ATTACHMENT = 36096;
    public static int GL_STENCIL_ATTACHMENT = 36128;
    public static int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
    public static int GL_FRAMEBUFFER_COMPLETE = 36053;
    public static int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    public static int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    public static int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    public static int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    public static int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    public static int GL_FRAMEBUFFER_UNDEFINED = 33305;
    public static int GL_FRAMEBUFFER_BINDING = 36006;
    public static int GL_RENDERBUFFER_BINDING = 36007;
    public static int GL_MAX_COLOR_ATTACHMENTS = 36063;
    public static int GL_MAX_RENDERBUFFER_SIZE = 34024;
    public static int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
    public static int GL_HALF_FLOAT = 5131;
    public static int GL_RENDERBUFFER_SAMPLES = 36011;
    public static int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
    public static int GL_MAX_SAMPLES = 36183;
    public static int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
    public static int GL_READ_FRAMEBUFFER_BINDING = 36010;
    public static int GL_RGBA_INTEGER_MODE = 36254;
    public static int GL_RGBA32UI = 36208;
    public static int GL_RGB32UI = 36209;
    public static int GL_ALPHA32UI = 36210;
    public static int GL_RGBA16UI = 36214;
    public static int GL_RGB16UI = 36215;
    public static int GL_ALPHA16UI = 36216;
    public static int GL_RGBA8UI = 36220;
    public static int GL_RGB8UI = 36221;
    public static int GL_ALPHA8UI = 36222;
    public static int GL_RGBA32I = 36226;
    public static int GL_RGB32I = 36227;
    public static int GL_ALPHA32I = 36228;
    public static int GL_RGBA16I = 36232;
    public static int GL_RGB16I = 36233;
    public static int GL_ALPHA16I = 36234;
    public static int GL_RGBA8I = 36238;
    public static int GL_RGB8I = 36239;
    public static int GL_ALPHA8I = 36240;
    public static int GL_RED_INTEGER = 36244;
    public static int GL_GREEN_INTEGER = 36245;
    public static int GL_BLUE_INTEGER = 36246;
    public static int GL_ALPHA_INTEGER = 36247;
    public static int GL_RGB_INTEGER = 36248;
    public static int GL_RGBA_INTEGER = 36249;
    public static int GL_BGR_INTEGER = 36250;
    public static int GL_BGRA_INTEGER = 36251;
    public static int GL_TEXTURE_1D_ARRAY = 35864;
    public static int GL_TEXTURE_2D_ARRAY = 35866;
    public static int GL_PROXY_TEXTURE_2D_ARRAY = 35867;
    public static int GL_PROXY_TEXTURE_1D_ARRAY = 35865;
    public static int GL_TEXTURE_BINDING_1D_ARRAY = 35868;
    public static int GL_TEXTURE_BINDING_2D_ARRAY = 35869;
    public static int GL_MAX_ARRAY_TEXTURE_LAYERS = 35071;
    public static int GL_COMPARE_REF_DEPTH_TO_TEXTURE = 34894;
    public static int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
    public static int GL_SAMPLER_1D_ARRAY = 36288;
    public static int GL_SAMPLER_2D_ARRAY = 36289;
    public static int GL_SAMPLER_1D_ARRAY_SHADOW = 36291;
    public static int GL_SAMPLER_2D_ARRAY_SHADOW = 36292;
    public static int GL_DEPTH_STENCIL = 34041;
    public static int GL_UNSIGNED_INT_24_8 = 34042;
    public static int GL_DEPTH24_STENCIL8 = 35056;
    public static int GL_TEXTURE_STENCIL_SIZE = 35057;
    public static int GL_COMPRESSED_RED_RGTC1 = 36283;
    public static int GL_COMPRESSED_SIGNED_RED_RGTC1 = 36284;
    public static int GL_COMPRESSED_RG_RGTC2 = 36285;
    public static int GL_COMPRESSED_SIGNED_RG_RGTC2 = 36286;
    public static int GL_R8 = 33321;
    public static int GL_R16 = 33322;
    public static int GL_RG8 = 33323;
    public static int GL_RG16 = 33324;
    public static int GL_R16F = 33325;
    public static int GL_R32F = 33326;
    public static int GL_RG16F = 33327;
    public static int GL_RG32F = 33328;
    public static int GL_R8I = 33329;
    public static int GL_R8UI = 33330;
    public static int GL_R16I = 33331;
    public static int GL_R16UI = 33332;
    public static int GL_R32I = 33333;
    public static int GL_R32UI = 33334;
    public static int GL_RG8I = 33335;
    public static int GL_RG8UI = 33336;
    public static int GL_RG16I = 33337;
    public static int GL_RG16UI = 33338;
    public static int GL_RG32I = 33339;
    public static int GL_RG32UI = 33340;
    public static int GL_RG = 33319;
    public static int GL_RG_INTEGER = 33320;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER = 35982;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_START = 35972;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 35973;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 35983;
    public static int GL_INTERLEAVED_ATTRIBS = 35980;
    public static int GL_SEPARATE_ATTRIBS = 35981;
    public static int GL_PRIMITIVES_GENERATED = 35975;
    public static int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 35976;
    public static int GL_RASTERIZER_DISCARD = 35977;
    public static int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 35978;
    public static int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 35979;
    public static int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 35968;
    public static int GL_TRANSFORM_FEEDBACK_VARYINGS = 35971;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 35967;
    public static int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 35958;
    public static int GL_VERTEX_ARRAY_BINDING = 34229;
    public static int GL_FRAMEBUFFER_SRGB = 36281;
    public static int GL_FRAMEBUFFER_SRGB_CAPABLE = 36282;
    public static int GL_RED_SNORM = 36752;
    public static int GL_RG_SNORM = 36753;
    public static int GL_RGB_SNORM = 36754;
    public static int GL_RGBA_SNORM = 36755;
    public static int GL_R8_SNORM = 36756;
    public static int GL_RG8_SNORM = 36757;
    public static int GL_RGB8_SNORM = 36758;
    public static int GL_RGBA8_SNORM = 36759;
    public static int GL_R16_SNORM = 36760;
    public static int GL_RG16_SNORM = 36761;
    public static int GL_RGB16_SNORM = 36762;
    public static int GL_RGBA16_SNORM = 36763;
    public static int GL_SIGNED_NORMALIZED = 36764;
    public static int GL_COPY_READ_BUFFER_BINDING = 36662;
    public static int GL_COPY_WRITE_BUFFER_BINDING = 36663;
    public static int GL_COPY_READ_BUFFER = 36662;
    public static int GL_COPY_WRITE_BUFFER = 36663;
    public static int GL_PRIMITIVE_RESTART = 36765;
    public static int GL_PRIMITIVE_RESTART_INDEX = 36766;
    public static int GL_TEXTURE_BUFFER = 35882;
    public static int GL_MAX_TEXTURE_BUFFER_SIZE = 35883;
    public static int GL_TEXTURE_BINDING_BUFFER = 35884;
    public static int GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 35885;
    public static int GL_TEXTURE_BUFFER_FORMAT = 35886;
    public static int GL_TEXTURE_RECTANGLE = 34037;
    public static int GL_TEXTURE_BINDING_RECTANGLE = 34038;
    public static int GL_PROXY_TEXTURE_RECTANGLE = 34039;
    public static int GL_MAX_RECTANGLE_TEXTURE_SIZE = 34040;
    public static int GL_SAMPLER_2D_RECT = 35683;
    public static int GL_SAMPLER_2D_RECT_SHADOW = 35684;
    public static int GL_UNIFORM_BUFFER = 35345;
    public static int GL_UNIFORM_BUFFER_BINDING = 35368;
    public static int GL_UNIFORM_BUFFER_START = 35369;
    public static int GL_UNIFORM_BUFFER_SIZE = 35370;
    public static int GL_MAX_VERTEX_UNIFORM_BLOCKS = 35371;
    public static int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 35372;
    public static int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 35373;
    public static int GL_MAX_COMBINED_UNIFORM_BLOCKS = 35374;
    public static int GL_MAX_UNIFORM_BUFFER_BINDINGS = 35375;
    public static int GL_MAX_UNIFORM_BLOCK_SIZE = 35376;
    public static int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 35377;
    public static int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 35378;
    public static int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 35379;
    public static int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 35380;
    public static int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 35381;
    public static int GL_ACTIVE_UNIFORM_BLOCKS = 35382;
    public static int GL_UNIFORM_TYPE = 35383;
    public static int GL_UNIFORM_SIZE = 35384;
    public static int GL_UNIFORM_NAME_LENGTH = 35385;
    public static int GL_UNIFORM_BLOCK_INDEX = 35386;
    public static int GL_UNIFORM_OFFSET = 35387;
    public static int GL_UNIFORM_ARRAY_STRIDE = 35388;
    public static int GL_UNIFORM_MATRIX_STRIDE = 35389;
    public static int GL_UNIFORM_IS_ROW_MAJOR = 35390;
    public static int GL_UNIFORM_BLOCK_BINDING = 35391;
    public static int GL_UNIFORM_BLOCK_DATA_SIZE = 35392;
    public static int GL_UNIFORM_BLOCK_NAME_LENGTH = 35393;
    public static int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 35394;
    public static int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 35395;
    public static int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 35396;
    public static int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 35397;
    public static int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 35398;
    public static int GL_INVALID_INDEX = -1;
    public static int GL_CONTEXT_PROFILE_MASK = 37158;
    public static int GL_CONTEXT_CORE_PROFILE_BIT = 1;
    public static int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
    public static int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
    public static int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
    public static int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
    public static int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
    public static int GL_FIRST_VERTEX_CONVENTION = 36429;
    public static int GL_LAST_VERTEX_CONVENTION = 36430;
    public static int GL_PROVOKING_VERTEX = 36431;
    public static int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
    public static int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
    public static int GL_SAMPLE_POSITION = 36432;
    public static int GL_SAMPLE_MASK = 36433;
    public static int GL_SAMPLE_MASK_VALUE = 36434;
    public static int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    public static int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    public static int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    public static int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    public static int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    public static int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    public static int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    public static int GL_MAX_INTEGER_SAMPLES = 37136;
    public static int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    public static int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    public static int GL_TEXTURE_SAMPLES = 37126;
    public static int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    public static int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    public static int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    public static int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    public static int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    public static int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    public static int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
    public static int GL_DEPTH_CLAMP = 34383;
    public static int GL_GEOMETRY_SHADER = 36313;
    public static int GL_GEOMETRY_VERTICES_OUT = 36314;
    public static int GL_GEOMETRY_INPUT_TYPE = 36315;
    public static int GL_GEOMETRY_OUTPUT_TYPE = 36316;
    public static int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
    public static int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
    public static int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
    public static int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
    public static int GL_LINES_ADJACENCY = 10;
    public static int GL_LINE_STRIP_ADJACENCY = 11;
    public static int GL_TRIANGLES_ADJACENCY = 12;
    public static int GL_TRIANGLE_STRIP_ADJACENCY = 13;
    public static int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
    public static int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
    public static int GL_PROGRAM_POINT_SIZE = 34370;
    public static int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static int GL_OBJECT_TYPE = 37138;
    public static int GL_SYNC_CONDITION = 37139;
    public static int GL_SYNC_STATUS = 37140;
    public static int GL_SYNC_FLAGS = 37141;
    public static int GL_SYNC_FENCE = 37142;
    public static int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static int GL_UNSIGNALED = 37144;
    public static int GL_SIGNALED = 37145;
    public static int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static long GL_TIMEOUT_IGNORED = -1;
    public static int GL_ALREADY_SIGNALED = 37146;
    public static int GL_TIMEOUT_EXPIRED = 37147;
    public static int GL_CONDITION_SATISFIED = 37148;
    public static int GL_WAIT_FAILED = 37149;
    public static int GL_SRC1_COLOR = 35065;
    public static int GL_ONE_MINUS_SRC1_COLOR = 35066;
    public static int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    public static int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
    public static int GL_ANY_SAMPLES_PASSED = 35887;
    public static int GL_SAMPLER_BINDING = 35097;
    public static int GL_RGB10_A2UI = 36975;
    public static int GL_TEXTURE_SWIZZLE_R = 36418;
    public static int GL_TEXTURE_SWIZZLE_G = 36419;
    public static int GL_TEXTURE_SWIZZLE_B = 36420;
    public static int GL_TEXTURE_SWIZZLE_A = 36421;
    public static int GL_TEXTURE_SWIZZLE_RGBA = 36422;
    public static int GL_TIME_ELAPSED = 35007;
    public static int GL_TIMESTAMP = 36392;
    public static int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
    public static int GL_INT_2_10_10_10_REV = 36255;
    public static int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;
    public static int GL_GEOMETRY_SHADER_INVOCATIONS = 34943;
    public static int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 36442;
    public static int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 36443;
    public static int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 36444;
    public static int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 36445;
    public static int GL_MAX_VERTEX_STREAMS = 36465;
    public static int GL_DOUBLE_VEC2 = 36860;
    public static int GL_DOUBLE_VEC3 = 36861;
    public static int GL_DOUBLE_VEC4 = 36862;
    public static int GL_DOUBLE_MAT2 = 36678;
    public static int GL_DOUBLE_MAT3 = 36679;
    public static int GL_DOUBLE_MAT4 = 36680;
    public static int GL_DOUBLE_MAT2x3 = 36681;
    public static int GL_DOUBLE_MAT2x4 = 36682;
    public static int GL_DOUBLE_MAT3x2 = 36683;
    public static int GL_DOUBLE_MAT3x4 = 36684;
    public static int GL_DOUBLE_MAT4x2 = 36685;
    public static int GL_DOUBLE_MAT4x3 = 36686;
    public static int GL_SAMPLE_SHADING = 35894;
    public static int GL_MIN_SAMPLE_SHADING_VALUE = 35895;
    public static int GL_ACTIVE_SUBROUTINES = 36325;
    public static int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
    public static int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
    public static int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
    public static int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
    public static int GL_MAX_SUBROUTINES = 36327;
    public static int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
    public static int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
    public static int GL_COMPATIBLE_SUBROUTINES = 36427;
    public static int GL_PATCHES = 14;
    public static int GL_PATCH_VERTICES = 36466;
    public static int GL_PATCH_DEFAULT_INNER_LEVEL = 36467;
    public static int GL_PATCH_DEFAULT_OUTER_LEVEL = 36468;
    public static int GL_TESS_CONTROL_OUTPUT_VERTICES = 36469;
    public static int GL_TESS_GEN_MODE = 36470;
    public static int GL_TESS_GEN_SPACING = 36471;
    public static int GL_TESS_GEN_VERTEX_ORDER = 36472;
    public static int GL_TESS_GEN_POINT_MODE = 36473;
    public static int GL_ISOLINES = 36474;
    public static int GL_FRACTIONAL_ODD = 36475;
    public static int GL_FRACTIONAL_EVEN = 36476;
    public static int GL_MAX_PATCH_VERTICES = 36477;
    public static int GL_MAX_TESS_GEN_LEVEL = 36478;
    public static int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 36479;
    public static int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 36480;
    public static int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 36481;
    public static int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 36482;
    public static int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 36483;
    public static int GL_MAX_TESS_PATCH_COMPONENTS = 36484;
    public static int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 36485;
    public static int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 36486;
    public static int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 36489;
    public static int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 36490;
    public static int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 34924;
    public static int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 34925;
    public static int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 36382;
    public static int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 36383;
    public static int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 34032;
    public static int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 34033;
    public static int GL_TESS_EVALUATION_SHADER = 36487;
    public static int GL_TESS_CONTROL_SHADER = 36488;
    public static int GL_TEXTURE_CUBE_MAP_ARRAY = 36873;
    public static int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 36874;
    public static int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = 36875;
    public static int GL_SAMPLER_CUBE_MAP_ARRAY = 36876;
    public static int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 36877;
    public static int GL_INT_SAMPLER_CUBE_MAP_ARRAY = 36878;
    public static int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 36879;
    public static int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = 36446;
    public static int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = 36447;
    public static int GL_MAX_PROGRAM_TEXTURE_GATHER_COMPONENTS_ARB = 36767;
    public static int GL_TRANSFORM_FEEDBACK = 36386;
    public static int GL_TRANSFORM_FEEDBACK_PAUSED = 36387;
    public static int GL_TRANSFORM_FEEDBACK_ACTIVE = 36388;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
    public static int GL_TRANSFORM_FEEDBACK_BINDING = 36389;
    public static int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
    public static int GL_SHADER_COMPILER = 36346;
    public static int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    public static int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    public static int GL_MAX_VARYING_VECTORS = 36348;
    public static int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    public static int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    public static int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    public static int GL_FIXED = 5132;
    public static int GL_LOW_FLOAT = 36336;
    public static int GL_MEDIUM_FLOAT = 36337;
    public static int GL_HIGH_FLOAT = 36338;
    public static int GL_LOW_INT = 36339;
    public static int GL_MEDIUM_INT = 36340;
    public static int GL_HIGH_INT = 36341;
    public static int GL_RGB565 = 36194;
    public static int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 33367;
    public static int GL_PROGRAM_BINARY_LENGTH = 34625;
    public static int GL_NUM_PROGRAM_BINARY_FORMATS = 34814;
    public static int GL_PROGRAM_BINARY_FORMATS = 34815;
    public static int GL_VERTEX_SHADER_BIT = 1;
    public static int GL_FRAGMENT_SHADER_BIT = 2;
    public static int GL_GEOMETRY_SHADER_BIT = 4;
    public static int GL_TESS_CONTROL_SHADER_BIT = 8;
    public static int GL_TESS_EVALUATION_SHADER_BIT = 16;
    public static int GL_ALL_SHADER_BITS = -1;
    public static int GL_PROGRAM_SEPARABLE = 33368;
    public static int GL_ACTIVE_PROGRAM = 33369;
    public static int GL_PROGRAM_PIPELINE_BINDING = 33370;
    public static int GL_MAX_VIEWPORTS = 33371;
    public static int GL_VIEWPORT_SUBPIXEL_BITS = 33372;
    public static int GL_VIEWPORT_BOUNDS_RANGE = 33373;
    public static int GL_LAYER_PROVOKING_VERTEX = 33374;
    public static int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 33375;
    public static int GL_UNDEFINED_VERTEX = 33376;
    public static int GL_COMPRESSED_RGBA_BPTC_UNORM = 36492;
    public static int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM = 36493;
    public static int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT = 36494;
    public static int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT = 36495;
    public static int GL_UNPACK_COMPRESSED_BLOCK_WIDTH = 37159;
    public static int GL_UNPACK_COMPRESSED_BLOCK_HEIGHT = 37160;
    public static int GL_UNPACK_COMPRESSED_BLOCK_DEPTH = 37161;
    public static int GL_UNPACK_COMPRESSED_BLOCK_SIZE = 37162;
    public static int GL_PACK_COMPRESSED_BLOCK_WIDTH = 37163;
    public static int GL_PACK_COMPRESSED_BLOCK_HEIGHT = 37164;
    public static int GL_PACK_COMPRESSED_BLOCK_DEPTH = 37165;
    public static int GL_PACK_COMPRESSED_BLOCK_SIZE = 37166;
    public static int GL_ATOMIC_COUNTER_BUFFER = 37568;
    public static int GL_ATOMIC_COUNTER_BUFFER_BINDING = 37569;
    public static int GL_ATOMIC_COUNTER_BUFFER_START = 37570;
    public static int GL_ATOMIC_COUNTER_BUFFER_SIZE = 37571;
    public static int GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE = 37572;
    public static int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS = 37573;
    public static int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES = 37574;
    public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER = 37575;
    public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER = 37576;
    public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER = 37577;
    public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER = 37578;
    public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER = 37579;
    public static int GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = 37580;
    public static int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = 37581;
    public static int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = 37582;
    public static int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = 37583;
    public static int GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = 37584;
    public static int GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = 37585;
    public static int GL_MAX_VERTEX_ATOMIC_COUNTERS = 37586;
    public static int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = 37587;
    public static int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = 37588;
    public static int GL_MAX_GEOMETRY_ATOMIC_COUNTERS = 37589;
    public static int GL_MAX_FRAGMENT_ATOMIC_COUNTERS = 37590;
    public static int GL_MAX_COMBINED_ATOMIC_COUNTERS = 37591;
    public static int GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = 37592;
    public static int GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = 37596;
    public static int GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = 37593;
    public static int GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX = 37594;
    public static int GL_UNSIGNED_INT_ATOMIC_COUNTER = 37595;
    public static int GL_TEXTURE_IMMUTABLE_FORMAT = 37167;
    public static int GL_MAX_IMAGE_UNITS = 36664;
    public static int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS = 36665;
    public static int GL_MAX_IMAGE_SAMPLES = 36973;
    public static int GL_MAX_VERTEX_IMAGE_UNIFORMS = 37066;
    public static int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS = 37067;
    public static int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS = 37068;
    public static int GL_MAX_GEOMETRY_IMAGE_UNIFORMS = 37069;
    public static int GL_MAX_FRAGMENT_IMAGE_UNIFORMS = 37070;
    public static int GL_MAX_COMBINED_IMAGE_UNIFORMS = 37071;
    public static int GL_IMAGE_BINDING_NAME = 36666;
    public static int GL_IMAGE_BINDING_LEVEL = 36667;
    public static int GL_IMAGE_BINDING_LAYERED = 36668;
    public static int GL_IMAGE_BINDING_LAYER = 36669;
    public static int GL_IMAGE_BINDING_ACCESS = 36670;
    public static int GL_IMAGE_BINDING_FORMAT = 36974;
    public static int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT = 1;
    public static int GL_ELEMENT_ARRAY_BARRIER_BIT = 2;
    public static int GL_UNIFORM_BARRIER_BIT = 4;
    public static int GL_TEXTURE_FETCH_BARRIER_BIT = 8;
    public static int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = 32;
    public static int GL_COMMAND_BARRIER_BIT = 64;
    public static int GL_PIXEL_BUFFER_BARRIER_BIT = 128;
    public static int GL_TEXTURE_UPDATE_BARRIER_BIT = 256;
    public static int GL_BUFFER_UPDATE_BARRIER_BIT = 512;
    public static int GL_FRAMEBUFFER_BARRIER_BIT = 1024;
    public static int GL_TRANSFORM_FEEDBACK_BARRIER_BIT = 2048;
    public static int GL_ATOMIC_COUNTER_BARRIER_BIT = 4096;
    public static int GL_ALL_BARRIER_BITS = -1;
    public static int GL_IMAGE_1D = 36940;
    public static int GL_IMAGE_2D = 36941;
    public static int GL_IMAGE_3D = 36942;
    public static int GL_IMAGE_2D_RECT = 36943;
    public static int GL_IMAGE_CUBE = 36944;
    public static int GL_IMAGE_BUFFER = 36945;
    public static int GL_IMAGE_1D_ARRAY = 36946;
    public static int GL_IMAGE_2D_ARRAY = 36947;
    public static int GL_IMAGE_CUBE_MAP_ARRAY = 36948;
    public static int GL_IMAGE_2D_MULTISAMPLE = 36949;
    public static int GL_IMAGE_2D_MULTISAMPLE_ARRAY = 36950;
    public static int GL_INT_IMAGE_1D = 36951;
    public static int GL_INT_IMAGE_2D = 36952;
    public static int GL_INT_IMAGE_3D = 36953;
    public static int GL_INT_IMAGE_2D_RECT = 36954;
    public static int GL_INT_IMAGE_CUBE = 36955;
    public static int GL_INT_IMAGE_BUFFER = 36956;
    public static int GL_INT_IMAGE_1D_ARRAY = 36957;
    public static int GL_INT_IMAGE_2D_ARRAY = 36958;
    public static int GL_INT_IMAGE_CUBE_MAP_ARRAY = 36959;
    public static int GL_INT_IMAGE_2D_MULTISAMPLE = 36960;
    public static int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 36961;
    public static int GL_UNSIGNED_INT_IMAGE_1D = 36962;
    public static int GL_UNSIGNED_INT_IMAGE_2D = 36963;
    public static int GL_UNSIGNED_INT_IMAGE_3D = 36964;
    public static int GL_UNSIGNED_INT_IMAGE_2D_RECT = 36965;
    public static int GL_UNSIGNED_INT_IMAGE_CUBE = 36966;
    public static int GL_UNSIGNED_INT_IMAGE_BUFFER = 36967;
    public static int GL_UNSIGNED_INT_IMAGE_1D_ARRAY = 36968;
    public static int GL_UNSIGNED_INT_IMAGE_2D_ARRAY = 36969;
    public static int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY = 36970;
    public static int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE = 36971;
    public static int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 36972;
    public static int GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = 37063;
    public static int GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE = 37064;
    public static int IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = 37065;
    public static int GL_NUM_SAMPLE_COUNTS = 37760;
    public static int GL_MIN_MAP_BUFFER_ALIGNMENT = 37052;
    public static int GL_NUM_SHADING_LANGUAGE_VERSIONS = 33513;
    public static int GL_VERTEX_ATTRIB_ARRAY_LONG = 34638;
    public static int GL_COMPRESSED_RGB8_ETC2 = 37492;
    public static int GL_COMPRESSED_SRGB8_ETC2 = 37493;
    public static int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37494;
    public static int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37495;
    public static int GL_COMPRESSED_RGBA8_ETC2_EAC = 37496;
    public static int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 37497;
    public static int GL_COMPRESSED_R11_EAC = 37488;
    public static int GL_COMPRESSED_SIGNED_R11_EAC = 37489;
    public static int GL_COMPRESSED_RG11_EAC = 37490;
    public static int GL_COMPRESSED_SIGNED_RG11_EAC = 37491;
    public static int GL_PRIMITIVE_RESTART_FIXED_INDEX = 36201;
    public static int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 36202;
    public static int GL_MAX_ELEMENT_INDEX = 36203;
    public static int GL_COMPUTE_SHADER = 37305;
    public static int GL_MAX_COMPUTE_UNIFORM_BLOCKS = 37307;
    public static int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 37308;
    public static int GL_MAX_COMPUTE_IMAGE_UNIFORMS = 37309;
    public static int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 33378;
    public static int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 33379;
    public static int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 33380;
    public static int GL_MAX_COMPUTE_ATOMIC_COUNTERS = 33381;
    public static int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 33382;
    public static int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 37099;
    public static int GL_MAX_COMPUTE_WORK_GROUP_COUNT = 37310;
    public static int GL_MAX_COMPUTE_WORK_GROUP_SIZE = 37311;
    public static int GL_COMPUTE_WORK_GROUP_SIZE = 33383;
    public static int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = 37100;
    public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = 37101;
    public static int GL_DISPATCH_INDIRECT_BUFFER = 37102;
    public static int GL_DISPATCH_INDIRECT_BUFFER_BINDING = 37103;
    public static int GL_COMPUTE_SHADER_BIT = 32;
    public static int GL_DEBUG_OUTPUT = 37600;
    public static int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
    public static int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
    public static int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
    public static int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
    public static int GL_DEBUG_LOGGED_MESSAGES = 37189;
    public static int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
    public static int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
    public static int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
    public static int GL_MAX_LABEL_LENGTH = 33512;
    public static int GL_DEBUG_CALLBACK_FUNCTION = 33348;
    public static int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
    public static int GL_DEBUG_SOURCE_API = 33350;
    public static int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    public static int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    public static int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    public static int GL_DEBUG_SOURCE_APPLICATION = 33354;
    public static int GL_DEBUG_SOURCE_OTHER = 33355;
    public static int GL_DEBUG_TYPE_ERROR = 33356;
    public static int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    public static int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    public static int GL_DEBUG_TYPE_PORTABILITY = 33359;
    public static int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    public static int GL_DEBUG_TYPE_OTHER = 33361;
    public static int GL_DEBUG_TYPE_MARKER = 33384;
    public static int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
    public static int GL_DEBUG_TYPE_POP_GROUP = 33386;
    public static int GL_DEBUG_SEVERITY_HIGH = 37190;
    public static int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    public static int GL_DEBUG_SEVERITY_LOW = 37192;
    public static int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    public static int GL_BUFFER = 33504;
    public static int GL_SHADER = 33505;
    public static int GL_PROGRAM = 33506;
    public static int GL_QUERY = 33507;
    public static int GL_PROGRAM_PIPELINE = 33508;
    public static int GL_SAMPLER = 33510;
    public static int GL_DISPLAY_LIST = 33511;
    public static int GL_MAX_UNIFORM_LOCATIONS = 33390;
    public static int GL_FRAMEBUFFER_DEFAULT_WIDTH = 37648;
    public static int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 37649;
    public static int GL_FRAMEBUFFER_DEFAULT_LAYERS = 37650;
    public static int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 37651;
    public static int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 37652;
    public static int GL_MAX_FRAMEBUFFER_WIDTH = 37653;
    public static int GL_MAX_FRAMEBUFFER_HEIGHT = 37654;
    public static int GL_MAX_FRAMEBUFFER_LAYERS = 37655;
    public static int GL_MAX_FRAMEBUFFER_SAMPLES = 37656;
    public static int GL_INTERNALFORMAT_SUPPORTED = 33391;
    public static int GL_INTERNALFORMAT_PREFERRED = 33392;
    public static int GL_INTERNALFORMAT_RED_SIZE = 33393;
    public static int GL_INTERNALFORMAT_GREEN_SIZE = 33394;
    public static int GL_INTERNALFORMAT_BLUE_SIZE = 33395;
    public static int GL_INTERNALFORMAT_ALPHA_SIZE = 33396;
    public static int GL_INTERNALFORMAT_DEPTH_SIZE = 33397;
    public static int GL_INTERNALFORMAT_STENCIL_SIZE = 33398;
    public static int GL_INTERNALFORMAT_SHARED_SIZE = 33399;
    public static int GL_INTERNALFORMAT_RED_TYPE = 33400;
    public static int GL_INTERNALFORMAT_GREEN_TYPE = 33401;
    public static int GL_INTERNALFORMAT_BLUE_TYPE = 33402;
    public static int GL_INTERNALFORMAT_ALPHA_TYPE = 33403;
    public static int GL_INTERNALFORMAT_DEPTH_TYPE = 33404;
    public static int GL_INTERNALFORMAT_STENCIL_TYPE = 33405;
    public static int GL_MAX_WIDTH = 33406;
    public static int GL_MAX_HEIGHT = 33407;
    public static int GL_MAX_DEPTH = 33408;
    public static int GL_MAX_LAYERS = 33409;
    public static int GL_MAX_COMBINED_DIMENSIONS = 33410;
    public static int GL_COLOR_COMPONENTS = 33411;
    public static int GL_DEPTH_COMPONENTS = 33412;
    public static int GL_STENCIL_COMPONENTS = 33413;
    public static int GL_COLOR_RENDERABLE = 33414;
    public static int GL_DEPTH_RENDERABLE = 33415;
    public static int GL_STENCIL_RENDERABLE = 33416;
    public static int GL_FRAMEBUFFER_RENDERABLE = 33417;
    public static int GL_FRAMEBUFFER_RENDERABLE_LAYERED = 33418;
    public static int GL_FRAMEBUFFER_BLEND = 33419;
    public static int GL_READ_PIXELS = 33420;
    public static int GL_READ_PIXELS_FORMAT = 33421;
    public static int GL_READ_PIXELS_TYPE = 33422;
    public static int GL_TEXTURE_IMAGE_FORMAT = 33423;
    public static int GL_TEXTURE_IMAGE_TYPE = 33424;
    public static int GL_GET_TEXTURE_IMAGE_FORMAT = 33425;
    public static int GL_GET_TEXTURE_IMAGE_TYPE = 33426;
    public static int GL_MIPMAP = 33427;
    public static int GL_MANUAL_GENERATE_MIPMAP = 33428;
    public static int GL_AUTO_GENERATE_MIPMAP = 33429;
    public static int GL_COLOR_ENCODING = 33430;
    public static int GL_SRGB_READ = 33431;
    public static int GL_SRGB_WRITE = 33432;
    public static int GL_SRGB_DECODE_ARB = 33433;
    public static int GL_FILTER = 33434;
    public static int GL_VERTEX_TEXTURE = 33435;
    public static int GL_TESS_CONTROL_TEXTURE = 33436;
    public static int GL_TESS_EVALUATION_TEXTURE = 33437;
    public static int GL_GEOMETRY_TEXTURE = 33438;
    public static int GL_FRAGMENT_TEXTURE = 33439;
    public static int GL_COMPUTE_TEXTURE = 33440;
    public static int GL_TEXTURE_SHADOW = 33441;
    public static int GL_TEXTURE_GATHER = 33442;
    public static int GL_TEXTURE_GATHER_SHADOW = 33443;
    public static int GL_SHADER_IMAGE_LOAD = 33444;
    public static int GL_SHADER_IMAGE_STORE = 33445;
    public static int GL_SHADER_IMAGE_ATOMIC = 33446;
    public static int GL_IMAGE_TEXEL_SIZE = 33447;
    public static int GL_IMAGE_COMPATIBILITY_CLASS = 33448;
    public static int GL_IMAGE_PIXEL_FORMAT = 33449;
    public static int GL_IMAGE_PIXEL_TYPE = 33450;
    public static int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = 33452;
    public static int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = 33453;
    public static int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = 33454;
    public static int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = 33455;
    public static int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = 33457;
    public static int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = 33458;
    public static int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = 33459;
    public static int GL_CLEAR_BUFFER = 33460;
    public static int GL_TEXTURE_VIEW = 33461;
    public static int GL_VIEW_COMPATIBILITY_CLASS = 33462;
    public static int GL_FULL_SUPPORT = 33463;
    public static int GL_CAVEAT_SUPPORT = 33464;
    public static int GL_IMAGE_CLASS_4_X_32 = 33465;
    public static int GL_IMAGE_CLASS_2_X_32 = 33466;
    public static int GL_IMAGE_CLASS_1_X_32 = 33467;
    public static int GL_IMAGE_CLASS_4_X_16 = 33468;
    public static int GL_IMAGE_CLASS_2_X_16 = 33469;
    public static int GL_IMAGE_CLASS_1_X_16 = 33470;
    public static int GL_IMAGE_CLASS_4_X_8 = 33471;
    public static int GL_IMAGE_CLASS_2_X_8 = 33472;
    public static int GL_IMAGE_CLASS_1_X_8 = 33473;
    public static int GL_IMAGE_CLASS_11_11_10 = 33474;
    public static int GL_IMAGE_CLASS_10_10_10_2 = 33475;
    public static int GL_VIEW_CLASS_128_BITS = 33476;
    public static int GL_VIEW_CLASS_96_BITS = 33477;
    public static int GL_VIEW_CLASS_64_BITS = 33478;
    public static int GL_VIEW_CLASS_48_BITS = 33479;
    public static int GL_VIEW_CLASS_32_BITS = 33480;
    public static int GL_VIEW_CLASS_24_BITS = 33481;
    public static int GL_VIEW_CLASS_16_BITS = 33482;
    public static int GL_VIEW_CLASS_8_BITS = 33483;
    public static int GL_VIEW_CLASS_S3TC_DXT1_RGB = 33484;
    public static int GL_VIEW_CLASS_S3TC_DXT1_RGBA = 33485;
    public static int GL_VIEW_CLASS_S3TC_DXT3_RGBA = 33486;
    public static int GL_VIEW_CLASS_S3TC_DXT5_RGBA = 33487;
    public static int GL_VIEW_CLASS_RGTC1_RED = 33488;
    public static int GL_VIEW_CLASS_RGTC2_RG = 33489;
    public static int GL_VIEW_CLASS_BPTC_UNORM = 33490;
    public static int GL_VIEW_CLASS_BPTC_FLOAT = 33491;
    public static int GL_UNIFORM = 37601;
    public static int GL_UNIFORM_BLOCK = 37602;
    public static int GL_PROGRAM_INPUT = 37603;
    public static int GL_PROGRAM_OUTPUT = 37604;
    public static int GL_BUFFER_VARIABLE = 37605;
    public static int GL_SHADER_STORAGE_BLOCK = 37606;
    public static int GL_VERTEX_SUBROUTINE = 37608;
    public static int GL_TESS_CONTROL_SUBROUTINE = 37609;
    public static int GL_TESS_EVALUATION_SUBROUTINE = 37610;
    public static int GL_GEOMETRY_SUBROUTINE = 37611;
    public static int GL_FRAGMENT_SUBROUTINE = 37612;
    public static int GL_COMPUTE_SUBROUTINE = 37613;
    public static int GL_VERTEX_SUBROUTINE_UNIFORM = 37614;
    public static int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 37615;
    public static int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 37616;
    public static int GL_GEOMETRY_SUBROUTINE_UNIFORM = 37617;
    public static int GL_FRAGMENT_SUBROUTINE_UNIFORM = 37618;
    public static int GL_COMPUTE_SUBROUTINE_UNIFORM = 37619;
    public static int GL_TRANSFORM_FEEDBACK_VARYING = 37620;
    public static int GL_ACTIVE_RESOURCES = 37621;
    public static int GL_MAX_NAME_LENGTH = 37622;
    public static int GL_MAX_NUM_ACTIVE_VARIABLES = 37623;
    public static int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 37624;
    public static int GL_NAME_LENGTH = 37625;
    public static int GL_TYPE = 37626;
    public static int GL_ARRAY_SIZE = 37627;
    public static int GL_OFFSET = 37628;
    public static int GL_BLOCK_INDEX = 37629;
    public static int GL_ARRAY_STRIDE = 37630;
    public static int GL_MATRIX_STRIDE = 37631;
    public static int GL_IS_ROW_MAJOR = 37632;
    public static int GL_ATOMIC_COUNTER_BUFFER_INDEX = 37633;
    public static int GL_BUFFER_BINDING = 37634;
    public static int GL_BUFFER_DATA_SIZE = 37635;
    public static int GL_NUM_ACTIVE_VARIABLES = 37636;
    public static int GL_ACTIVE_VARIABLES = 37637;
    public static int GL_REFERENCED_BY_VERTEX_SHADER = 37638;
    public static int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 37639;
    public static int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 37640;
    public static int GL_REFERENCED_BY_GEOMETRY_SHADER = 37641;
    public static int GL_REFERENCED_BY_FRAGMENT_SHADER = 37642;
    public static int GL_REFERENCED_BY_COMPUTE_SHADER = 37643;
    public static int GL_TOP_LEVEL_ARRAY_SIZE = 37644;
    public static int GL_TOP_LEVEL_ARRAY_STRIDE = 37645;
    public static int GL_LOCATION = 37646;
    public static int GL_LOCATION_INDEX = 37647;
    public static int GL_IS_PER_PATCH = 37607;
    public static int GL_SHADER_STORAGE_BUFFER = 37074;
    public static int GL_SHADER_STORAGE_BUFFER_BINDING = 37075;
    public static int GL_SHADER_STORAGE_BUFFER_START = 37076;
    public static int GL_SHADER_STORAGE_BUFFER_SIZE = 37077;
    public static int GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = 37078;
    public static int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = 37079;
    public static int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = 37080;
    public static int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = 37081;
    public static int GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = 37082;
    public static int GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = 37083;
    public static int GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = 37084;
    public static int GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = 37085;
    public static int GL_MAX_SHADER_STORAGE_BLOCK_SIZE = 37086;
    public static int GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = 37087;
    public static int GL_SHADER_STORAGE_BARRIER_BIT = 8192;
    public static int GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = 36665;
    public static int GL_DEPTH_STENCIL_TEXTURE_MODE = 37098;
    public static int GL_TEXTURE_BUFFER_OFFSET = 37277;
    public static int GL_TEXTURE_BUFFER_SIZE = 37278;
    public static int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;
    public static int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
    public static int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
    public static int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
    public static int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
    public static int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;
    public static int GL_VERTEX_ATTRIB_BINDING = 33492;
    public static int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
    public static int GL_VERTEX_BINDING_DIVISOR = 33494;
    public static int GL_VERTEX_BINDING_OFFSET = 33495;
    public static int GL_VERTEX_BINDING_STRIDE = 33496;
    public static int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
    public static int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;
    public static int GL_MAX_VERTEX_ATTRIB_STRIDE = 33509;
    public static int GL_MAP_PERSISTENT_BIT = 64;
    public static int GL_MAP_COHERENT_BIT = 128;
    public static int GL_DYNAMIC_STORAGE_BIT = 256;
    public static int GL_CLIENT_STORAGE_BIT = 512;
    public static int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
    public static int GL_BUFFER_STORAGE_FLAGS = 33312;
    public static int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;
    public static int GL_CLEAR_TEXTURE = 37733;
    public static int GL_LOCATION_COMPONENT = 37706;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = 37707;
    public static int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = 37708;
    public static int GL_QUERY_RESULT_NO_WAIT = 37268;
    public static int GL_QUERY_BUFFER = 37266;
    public static int GL_QUERY_BUFFER_BINDING = 37267;
    public static int GL_QUERY_BUFFER_BARRIER_BIT = 32768;
    public static int GL_MIRROR_CLAMP_TO_EDGE = 34627;

    public static void glPixelTransferi(int a, int b) {
        GL11.glPixelTransferi(a, b);
    }

    public static void glPolygonStipple(long a) {
        GL11.glPolygonStipple(a);
    }

    public static void glPolygonStipple(ByteBuffer a) {
        GL11.glPolygonStipple(a);
    }

    public static void glPixelTransferf(int a, float b) {
        GL11.glPixelTransferf(a, b);
    }

    public static int glGetTexParameteri(int a, int b) {
        return GL11.glGetTexParameteri(a, b);
    }

    public static void glGetPolygonStipple(long a) {
        GL11.glGetPolygonStipple(a);
    }

    public static void glGetPolygonStipple(ByteBuffer a) {
        GL11.glGetPolygonStipple(a);
    }

    public static void glInterleavedArrays(int a, int b, ShortBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, IntBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, ByteBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, FloatBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, DoubleBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, long c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static int glGetTexLevelParameteri(int a, int b, int c) {
        return GL11.glGetTexLevelParameteri(a, b, c);
    }

    public static void glGetClipPlane(int a, DoubleBuffer b) {
        GL11.glGetClipPlane(a, b);
    }

    public static void glGetTexLevelParameter(int a, int b, int c, IntBuffer d) {
        GL11.glGetTexLevelParameter(a, b, c, d);
    }

    public static void glGetTexLevelParameter(int a, int b, int c, FloatBuffer d) {
        GL11.glGetTexLevelParameter(a, b, c, d);
    }

    public static void glGetTexParameter(int a, int b, FloatBuffer c) {
        GL11.glGetTexParameter(a, b, c);
    }

    public static void glGetTexParameter(int a, int b, IntBuffer c) {
        GL11.glGetTexParameter(a, b, c);
    }

    public static void glLoadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void glPolygonOffset(float a, float b) {
        GL11.glPolygonOffset(a, b);
    }

    public static float glGetTexParameterf(int a, int b) {
        return GL11.glGetTexParameterf(a, b);
    }

    public static float glGetTexLevelParameterf(int a, int b, int c) {
        return GL11.glGetTexLevelParameterf(a, b, c);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, DoubleBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ByteBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, FloatBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, IntBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ShortBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, ShortBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, FloatBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, DoubleBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, ByteBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, IntBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, long g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexParameteri(int a, int b, int c) {
        GL11.glTexParameteri(a, b, c);
    }

    public static void glNormalPointer(int a, DoubleBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glNormalPointer(int a, int b, long c) {
        GL11.glNormalPointer(a, b, c);
    }

    public static void glNormalPointer(int a, FloatBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glNormalPointer(int a, int b, ByteBuffer c) {
        GL11.glNormalPointer(a, b, c);
    }

    public static void glNormalPointer(int a, IntBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glNormalPointer(int a, ByteBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glPushClientAttrib(int a) {
        GL11.glPushClientAttrib(a);
    }

    public static void glPopClientAttrib() {
        GL11.glPopClientAttrib();
    }

    public static void glTexParameter(int a, int b, IntBuffer c) {
        GL11.glTexParameter(a, b, c);
    }

    public static void glTexParameter(int a, int b, FloatBuffer c) {
        GL11.glTexParameter(a, b, c);
    }

    public static void glVertexPointer(int a, int b, int c, ByteBuffer d) {
        GL11.glVertexPointer(a, b, c, d);
    }

    public static void glVertexPointer(int a, int b, int c, long d) {
        GL11.glVertexPointer(a, b, c, d);
    }

    public static void glVertexPointer(int a, int b, ShortBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glVertexPointer(int a, int b, DoubleBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glVertexPointer(int a, int b, FloatBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glVertexPointer(int a, int b, IntBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, DoubleBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, IntBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, FloatBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, int c, long d) {
        GL11.glTexCoordPointer(a, b, c, d);
    }

    public static void glTexCoordPointer(int a, int b, ShortBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, int c, ByteBuffer d) {
        GL11.glTexCoordPointer(a, b, c, d);
    }

    public static void glTexParameterf(int a, int b, float c) {
        GL11.glTexParameterf(a, b, c);
    }

    public static void glSelectBuffer(IntBuffer a) {
        GL11.glSelectBuffer(a);
    }

    public static void glGetPixelMapusv(int a, long b) {
        GL11.glGetPixelMapusv(a, b);
    }

    public static void glGetPixelMapuiv(int a, long b) {
        GL11.glGetPixelMapuiv(a, b);
    }

    public static void glDrawElements(int a, int b, int c, long d) {
        GL11.glDrawElements(a, b, c, d);
    }

    public static void glDrawElements(int a, IntBuffer b) {
        GL11.glDrawElements(a, b);
    }

    public static void glDrawElements(int a, ByteBuffer b) {
        GL11.glDrawElements(a, b);
    }

    public static void glDrawElements(int a, ShortBuffer b) {
        GL11.glDrawElements(a, b);
    }

    public static void glDrawElements(int a, int b, int c, ByteBuffer d) {
        GL11.glDrawElements(a, b, c, d);
    }

    public static void glEnableClientState(int a) {
        GL11.glEnableClientState(a);
    }

    public static void glClearStencil(int a) {
        GL11.glClearStencil(a);
    }

    public static void glDisableClientState(int a) {
        GL11.glDisableClientState(a);
    }

    public static void glEdgeFlagPointer(int a, ByteBuffer b) {
        GL11.glEdgeFlagPointer(a, b);
    }

    public static void glEdgeFlagPointer(int a, long b) {
        GL11.glEdgeFlagPointer(a, b);
    }

    public static void glGetPixelMapfv(int a, long b) {
        GL11.glGetPixelMapfv(a, b);
    }

    public static void glGetPixelMapu(int a, ShortBuffer b) {
        GL11.glGetPixelMapu(a, b);
    }

    public static void glGetPixelMapu(int a, IntBuffer b) {
        GL11.glGetPixelMapu(a, b);
    }

    public static void glFeedbackBuffer(int a, FloatBuffer b) {
        GL11.glFeedbackBuffer(a, b);
    }

    public static void glClearAccum(float a, float b, float c, float d) {
        GL11.glClearAccum(a, b, c, d);
    }

    public static void glClear(int a) {
        GL11.glClear(a);
    }

    public static void glCallList(int a) {
        GL11.glCallList(a);
    }

    public static void glCallLists(ShortBuffer a) {
        GL11.glCallLists(a);
    }

    public static void glCallLists(IntBuffer a) {
        GL11.glCallLists(a);
    }

    public static void glCallLists(ByteBuffer a) {
        GL11.glCallLists(a);
    }

    public static void glAccum(int a, float b) {
        GL11.glAccum(a, b);
    }

    public static void glClearColor(float a, float b, float c, float d) {
        GL11.glClearColor(a, b, c, d);
    }

    public static void glAlphaFunc(int a, float b) {
        GL11.glAlphaFunc(a, b);
    }

    public static void glDeleteLists(int a, int b) {
        GL11.glDeleteLists(a, b);
    }

    public static void glCullFace(int a) {
        GL11.glCullFace(a);
    }

    public static void glColorMask(boolean a, boolean b, boolean c, boolean d) {
        GL11.glColorMask(a, b, c, d);
    }

    public static void glBitmap(int a, int b, float c, float d, float e, float f, ByteBuffer g) {
        GL11.glBitmap(a, b, c, d, e, f, g);
    }

    public static void glBitmap(int a, int b, float c, float d, float e, float f, long g) {
        GL11.glBitmap(a, b, c, d, e, f, g);
    }

    public static void glBlendFunc(int a, int b) {
        GL11.glBlendFunc(a, b);
    }

    public static void glClearDepth(double a) {
        GL11.glClearDepth(a);
    }

    public static void glCopyPixels(int a, int b, int c, int d, int e) {
        GL11.glCopyPixels(a, b, c, d, e);
    }

    public static void glBegin(int a) {
        GL11.glBegin(a);
    }

    public static void glBindTexture(int a, int b) {
        GL11.glBindTexture(a, b);
    }

    public static void glEnd() {
        GL11.glEnd();
    }

    public static void glPrioritizeTextures(IntBuffer a, FloatBuffer b) {
        GL11.glPrioritizeTextures(a, b);
    }

    public static void glArrayElement(int a) {
        GL11.glArrayElement(a);
    }

    public static void glDeleteTextures(IntBuffer a) {
        GL11.glDeleteTextures(a);
    }

    public static void glDeleteTextures(int a) {
        GL11.glDeleteTextures(a);
    }

    public static void glCopyTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL11.glCopyTexSubImage2D(a, b, c, d, e, f, g, h);
    }

    public static boolean glAreTexturesResident(IntBuffer a, ByteBuffer b) {
        return GL11.glAreTexturesResident(a, b);
    }

    public static void glCopyTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL11.glCopyTexImage2D(a, b, c, d, e, f, g, h);
    }

    public static void glCopyTexSubImage1D(int a, int b, int c, int d, int e, int f) {
        GL11.glCopyTexSubImage1D(a, b, c, d, e, f);
    }

    public static void glCopyTexImage1D(int a, int b, int c, int d, int e, int f, int g) {
        GL11.glCopyTexImage1D(a, b, c, d, e, f, g);
    }

    public static void glColorPointer(int a, int b, DoubleBuffer c) {
        GL11.glColorPointer(a, b, c);
    }

    public static void glColorPointer(int a, int b, int c, long d) {
        GL11.glColorPointer(a, b, c, d);
    }

    public static void glColorPointer(int a, boolean b, int c, ByteBuffer d) {
        GL11.glColorPointer(a, b, c, d);
    }

    public static void glColorPointer(int a, int b, FloatBuffer c) {
        GL11.glColorPointer(a, b, c);
    }

    public static void glColorPointer(int a, int b, int c, ByteBuffer d) {
        GL11.glColorPointer(a, b, c, d);
    }

    public static void glColorMaterial(int a, int b) {
        GL11.glColorMaterial(a, b);
    }

    public static int glGetTexGeni(int a, int b) {
        return GL11.glGetTexGeni(a, b);
    }

    public static void glMap2f(int a, float b, float c, int d, int e, float f, float g, int h, int i, FloatBuffer j) {
        GL11.glMap2f(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glGetTexEnv(int a, int b, IntBuffer c) {
        GL11.glGetTexEnv(a, b, c);
    }

    public static void glGetTexEnv(int a, int b, FloatBuffer c) {
        GL11.glGetTexEnv(a, b, c);
    }

    public static float glGetTexEnvf(int a, int b) {
        return GL11.glGetTexEnvf(a, b);
    }

    public static void glMapGrid1d(int a, double b, double c) {
        GL11.glMapGrid1d(a, b, c);
    }

    public static void glColor3b(byte a, byte b, byte c) {
        GL11.glColor3b(a, b, c);
    }

    public static void glHint(int a, int b) {
        GL11.glHint(a, b);
    }

    public static void glMaterial(int a, int b, IntBuffer c) {
        GL11.glMaterial(a, b, c);
    }

    public static void glMaterial(int a, int b, FloatBuffer c) {
        GL11.glMaterial(a, b, c);
    }

    public static int glGetTexEnvi(int a, int b) {
        return GL11.glGetTexEnvi(a, b);
    }

    public static void glMapGrid1f(int a, float b, float c) {
        GL11.glMapGrid1f(a, b, c);
    }

    public static void glMapGrid2d(int a, double b, double c, int d, double e, double f) {
        GL11.glMapGrid2d(a, b, c, d, e, f);
    }

    public static void glInitNames() {
        GL11.glInitNames();
    }

    public static ByteBuffer glGetPointer(int a, long b) {
        return GL11.glGetPointer(a, b);
    }

    public static void glMaterialf(int a, int b, float c) {
        GL11.glMaterialf(a, b, c);
    }

    public static double glGetTexGend(int a, int b) {
        return GL11.glGetTexGend(a, b);
    }

    public static boolean glIsEnabled(int a) {
        return GL11.glIsEnabled(a);
    }

    public static void glGetTexImage(int a, int b, int c, int d, ByteBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, FloatBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, DoubleBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, IntBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, ShortBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, long e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexGen(int a, int b, DoubleBuffer c) {
        GL11.glGetTexGen(a, b, c);
    }

    public static void glGetTexGen(int a, int b, FloatBuffer c) {
        GL11.glGetTexGen(a, b, c);
    }

    public static void glGetTexGen(int a, int b, IntBuffer c) {
        GL11.glGetTexGen(a, b, c);
    }

    public static float glGetTexGenf(int a, int b) {
        return GL11.glGetTexGenf(a, b);
    }

    public static boolean glIsList(int a) {
        return GL11.glIsList(a);
    }

    public static String glGetString(int a) {
        return GL11.glGetString(a);
    }

    public static void glMapGrid2f(int a, float b, float c, int d, float e, float f) {
        GL11.glMapGrid2f(a, b, c, d, e, f);
    }

    public static void glMateriali(int a, int b, int c) {
        GL11.glMateriali(a, b, c);
    }

    public static void glListBase(int a) {
        GL11.glListBase(a);
    }

    public static void glLineWidth(float a) {
        GL11.glLineWidth(a);
    }

    public static void glMap1d(int a, double b, double c, int d, int e, DoubleBuffer f) {
        GL11.glMap1d(a, b, c, d, e, f);
    }

    public static void glLightModeli(int a, int b) {
        GL11.glLightModeli(a, b);
    }

    public static void glLightf(int a, int b, float c) {
        GL11.glLightf(a, b, c);
    }

    public static void glLighti(int a, int b, int c) {
        GL11.glLighti(a, b, c);
    }

    public static void glMap1f(int a, float b, float c, int d, int e, FloatBuffer f) {
        GL11.glMap1f(a, b, c, d, e, f);
    }

    public static void glPointSize(float a) {
        GL11.glPointSize(a);
    }

    public static void glMatrixMode(int a) {
        GL11.glMatrixMode(a);
    }

    public static void glPixelZoom(float a, float b) {
        GL11.glPixelZoom(a, b);
    }

    public static void glPixelStoref(int a, float b) {
        GL11.glPixelStoref(a, b);
    }

    public static void glPixelStorei(int a, int b) {
        GL11.glPixelStorei(a, b);
    }

    public static void glPixelMap(int a, FloatBuffer b) {
        GL11.glPixelMap(a, b);
    }

    public static void glPixelMapu(int a, IntBuffer b) {
        GL11.glPixelMapu(a, b);
    }

    public static void glPixelMapu(int a, ShortBuffer b) {
        GL11.glPixelMapu(a, b);
    }

    public static void glPixelMapuiv(int a, int b, long c) {
        GL11.glPixelMapuiv(a, b, c);
    }

    public static void glPolygonMode(int a, int b) {
        GL11.glPolygonMode(a, b);
    }

    public static void glPixelMapusv(int a, int b, long c) {
        GL11.glPixelMapusv(a, b, c);
    }

    public static void glPassThrough(float a) {
        GL11.glPassThrough(a);
    }

    public static void glOrtho(double a, double b, double c, double d, double e, double f) {
        GL11.glOrtho(a, b, c, d, e, f);
    }

    public static void glLight(int a, int b, IntBuffer c) {
        GL11.glLight(a, b, c);
    }

    public static void glLight(int a, int b, FloatBuffer c) {
        GL11.glLight(a, b, c);
    }

    public static void glLoadName(int a) {
        GL11.glLoadName(a);
    }

    public static void glLogicOp(int a) {
        GL11.glLogicOp(a);
    }

    public static boolean glIsTexture(int a) {
        return GL11.glIsTexture(a);
    }

    public static void glNormal3b(byte a, byte b, byte c) {
        GL11.glNormal3b(a, b, c);
    }

    public static void glLineStipple(int a, short b) {
        GL11.glLineStipple(a, b);
    }

    public static void glMap2d(int a, double b, double c, int d, int e, double f, double g, int h, int i, DoubleBuffer j) {
        GL11.glMap2d(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glLightModel(int a, FloatBuffer b) {
        GL11.glLightModel(a, b);
    }

    public static void glLightModel(int a, IntBuffer b) {
        GL11.glLightModel(a, b);
    }

    public static void glLoadMatrix(FloatBuffer a) {
        GL11.glLoadMatrix(a);
    }

    public static void glLoadMatrix(DoubleBuffer a) {
        GL11.glLoadMatrix(a);
    }

    public static void glPixelMapfv(int a, int b, long c) {
        GL11.glPixelMapfv(a, b, c);
    }

    public static void glLightModelf(int a, float b) {
        GL11.glLightModelf(a, b);
    }

    public static void glEvalCoord2d(double a, double b) {
        GL11.glEvalCoord2d(a, b);
    }

    public static void glEnable(int a) {
        GL11.glEnable(a);
    }

    public static void glColor3d(double a, double b, double c) {
        GL11.glColor3d(a, b, c);
    }

    public static void glEvalCoord1f(float a) {
        GL11.glEvalCoord1f(a);
    }

    public static void glDisable(int a) {
        GL11.glDisable(a);
    }

    public static void glEvalCoord1d(double a) {
        GL11.glEvalCoord1d(a);
    }

    public static void glEdgeFlag(boolean a) {
        GL11.glEdgeFlag(a);
    }

    public static void glEvalCoord2f(float a, float b) {
        GL11.glEvalCoord2f(a, b);
    }

    public static void glDrawPixels(int a, int b, int c, int d, ByteBuffer e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glDrawPixels(int a, int b, int c, int d, IntBuffer e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glDrawPixels(int a, int b, int c, int d, ShortBuffer e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glDrawPixels(int a, int b, int c, int d, long e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glColor3f(float a, float b, float c) {
        GL11.glColor3f(a, b, c);
    }

    public static void glColor4ub(byte a, byte b, byte c, byte d) {
        GL11.glColor4ub(a, b, c, d);
    }

    public static void glEvalMesh2(int a, int b, int c, int d, int e) {
        GL11.glEvalMesh2(a, b, c, d, e);
    }

    public static void glColor4f(float a, float b, float c, float d) {
        GL11.glColor4f(a, b, c, d);
    }

    public static void glEvalPoint1(int a) {
        GL11.glEvalPoint1(a);
    }

    public static void glEvalMesh1(int a, int b, int c) {
        GL11.glEvalMesh1(a, b, c);
    }

    public static void glColor4d(double a, double b, double c, double d) {
        GL11.glColor4d(a, b, c, d);
    }

    public static void glColor3ub(byte a, byte b, byte c) {
        GL11.glColor3ub(a, b, c);
    }

    public static void glEvalPoint2(int a, int b) {
        GL11.glEvalPoint2(a, b);
    }

    public static void glColor4b(byte a, byte b, byte c, byte d) {
        GL11.glColor4b(a, b, c, d);
    }

    public static void glClipPlane(int a, DoubleBuffer b) {
        GL11.glClipPlane(a, b);
    }

    public static void glDepthMask(boolean a) {
        GL11.glDepthMask(a);
    }

    public static void glGenTextures(IntBuffer a) {
        GL11.glGenTextures(a);
    }

    public static int glGenTextures() {
        return GL11.glGenTextures();
    }

    public static void glFrontFace(int a) {
        GL11.glFrontFace(a);
    }

    public static void glFogi(int a, int b) {
        GL11.glFogi(a, b);
    }

    public static int glGenLists(int a) {
        return GL11.glGenLists(a);
    }

    public static void glFrustum(double a, double b, double c, double d, double e, double f) {
        GL11.glFrustum(a, b, c, d, e, f);
    }

    public static void glFog(int a, FloatBuffer b) {
        GL11.glFog(a, b);
    }

    public static void glFog(int a, IntBuffer b) {
        GL11.glFog(a, b);
    }

    public static void glFlush() {
        GL11.glFlush();
    }

    public static void glFinish() {
        GL11.glFinish();
    }

    public static void glFogf(int a, float b) {
        GL11.glFogf(a, b);
    }

    public static int glGetInteger(int a) {
        return GL11.glGetInteger(a);
    }

    public static void glGetInteger(int a, IntBuffer b) {
        GL11.glGetInteger(a, b);
    }

    public static void glDepthFunc(int a) {
        GL11.glDepthFunc(a);
    }

    public static void glGetFloat(int a, FloatBuffer b) {
        GL11.glGetFloat(a, b);
    }

    public static float glGetFloat(int a) {
        return GL11.glGetFloat(a);
    }

    public static void glGetMaterial(int a, int b, IntBuffer c) {
        GL11.glGetMaterial(a, b, c);
    }

    public static void glGetMaterial(int a, int b, FloatBuffer c) {
        GL11.glGetMaterial(a, b, c);
    }

    public static void glDrawBuffer(int a) {
        GL11.glDrawBuffer(a);
    }

    public static void glGetPixelMap(int a, FloatBuffer b) {
        GL11.glGetPixelMap(a, b);
    }

    public static void glGetMap(int a, int b, FloatBuffer c) {
        GL11.glGetMap(a, b, c);
    }

    public static void glGetMap(int a, int b, DoubleBuffer c) {
        GL11.glGetMap(a, b, c);
    }

    public static void glGetMap(int a, int b, IntBuffer c) {
        GL11.glGetMap(a, b, c);
    }

    public static void glGetLight(int a, int b, IntBuffer c) {
        GL11.glGetLight(a, b, c);
    }

    public static void glGetLight(int a, int b, FloatBuffer c) {
        GL11.glGetLight(a, b, c);
    }

    public static int glGetError() {
        return GL11.glGetError();
    }

    public static void glGetBoolean(int a, ByteBuffer b) {
        GL11.glGetBoolean(a, b);
    }

    public static boolean glGetBoolean(int a) {
        return GL11.glGetBoolean(a);
    }

    public static void glGetDouble(int a, DoubleBuffer b) {
        GL11.glGetDouble(a, b);
    }

    public static double glGetDouble(int a) {
        return GL11.glGetDouble(a);
    }

    public static void glDepthRange(double a, double b) {
        GL11.glDepthRange(a, b);
    }

    public static void glDrawArrays(int a, int b, int c) {
        GL11.glDrawArrays(a, b, c);
    }

    public static void glTexCoord4d(double a, double b, double c, double d) {
        GL11.glTexCoord4d(a, b, c, d);
    }

    public static void glTexCoord4f(float a, float b, float c, float d) {
        GL11.glTexCoord4f(a, b, c, d);
    }

    public static void glStencilMask(int a) {
        GL11.glStencilMask(a);
    }

    public static void glStencilOp(int a, int b, int c) {
        GL11.glStencilOp(a, b, c);
    }

    public static void glViewport(int a, int b, int c, int d) {
        GL11.glViewport(a, b, c, d);
    }

    public static void glTexCoord3d(double a, double b, double c) {
        GL11.glTexCoord3d(a, b, c);
    }

    public static void glNormal3f(float a, float b, float c) {
        GL11.glNormal3f(a, b, c);
    }

    public static void glRasterPos4f(float a, float b, float c, float d) {
        GL11.glRasterPos4f(a, b, c, d);
    }

    public static void glRasterPos4i(int a, int b, int c, int d) {
        GL11.glRasterPos4i(a, b, c, d);
    }

    public static void glPushName(int a) {
        GL11.glPushName(a);
    }

    public static void glRasterPos2i(int a, int b) {
        GL11.glRasterPos2i(a, b);
    }

    public static void glRectd(double a, double b, double c, double d) {
        GL11.glRectd(a, b, c, d);
    }

    public static void glRectf(float a, float b, float c, float d) {
        GL11.glRectf(a, b, c, d);
    }

    public static void glPopName() {
        GL11.glPopName();
    }

    public static void glNewList(int a, int b) {
        GL11.glNewList(a, b);
    }

    public static void glRasterPos4d(double a, double b, double c, double d) {
        GL11.glRasterPos4d(a, b, c, d);
    }

    public static void glPushMatrix() {
        GL11.glPushMatrix();
    }

    public static void glScaled(double a, double b, double c) {
        GL11.glScaled(a, b, c);
    }

    public static void glPopMatrix() {
        GL11.glPopMatrix();
    }

    public static void glPushAttrib(int a) {
        GL11.glPushAttrib(a);
    }

    public static void glRotated(double a, double b, double c, double d) {
        GL11.glRotated(a, b, c, d);
    }

    public static void glNormal3d(double a, double b, double c) {
        GL11.glNormal3d(a, b, c);
    }

    public static void glScalef(float a, float b, float c) {
        GL11.glScalef(a, b, c);
    }

    public static void glReadBuffer(int a) {
        GL11.glReadBuffer(a);
    }

    public static void glRasterPos2d(double a, double b) {
        GL11.glRasterPos2d(a, b);
    }

    public static void glShadeModel(int a) {
        GL11.glShadeModel(a);
    }

    public static void glRasterPos3d(double a, double b, double c) {
        GL11.glRasterPos3d(a, b, c);
    }

    public static void glScissor(int a, int b, int c, int d) {
        GL11.glScissor(a, b, c, d);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, DoubleBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, ByteBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, long g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, ShortBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, IntBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, FloatBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glRotatef(float a, float b, float c, float d) {
        GL11.glRotatef(a, b, c, d);
    }

    public static void glNormal3i(int a, int b, int c) {
        GL11.glNormal3i(a, b, c);
    }

    public static void glRecti(int a, int b, int c, int d) {
        GL11.glRecti(a, b, c, d);
    }

    public static void glRasterPos2f(float a, float b) {
        GL11.glRasterPos2f(a, b);
    }

    public static void glRasterPos3f(float a, float b, float c) {
        GL11.glRasterPos3f(a, b, c);
    }

    public static void glRasterPos3i(int a, int b, int c) {
        GL11.glRasterPos3i(a, b, c);
    }

    public static void glMultMatrix(FloatBuffer a) {
        GL11.glMultMatrix(a);
    }

    public static void glMultMatrix(DoubleBuffer a) {
        GL11.glMultMatrix(a);
    }

    public static int glRenderMode(int a) {
        return GL11.glRenderMode(a);
    }

    public static void glEndList() {
        GL11.glEndList();
    }

    public static void glVertex2f(float a, float b) {
        GL11.glVertex2f(a, b);
    }

    public static void glTexCoord1d(double a) {
        GL11.glTexCoord1d(a);
    }

    public static void glTexCoord2f(float a, float b) {
        GL11.glTexCoord2f(a, b);
    }

    public static void glVertex3f(float a, float b, float c) {
        GL11.glVertex3f(a, b, c);
    }

    public static void glPopAttrib() {
        GL11.glPopAttrib();
    }

    public static void glVertex3i(int a, int b, int c) {
        GL11.glVertex3i(a, b, c);
    }

    public static void glVertex4i(int a, int b, int c, int d) {
        GL11.glVertex4i(a, b, c, d);
    }

    public static void glTexEnvi(int a, int b, int c) {
        GL11.glTexEnvi(a, b, c);
    }

    public static void glVertex4f(float a, float b, float c, float d) {
        GL11.glVertex4f(a, b, c, d);
    }

    public static void glVertex3d(double a, double b, double c) {
        GL11.glVertex3d(a, b, c);
    }

    public static void glVertex4d(double a, double b, double c, double d) {
        GL11.glVertex4d(a, b, c, d);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, IntBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, FloatBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, DoubleBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ByteBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ShortBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexGend(int a, int b, double c) {
        GL11.glTexGend(a, b, c);
    }

    public static void glTexGeni(int a, int b, int c) {
        GL11.glTexGeni(a, b, c);
    }

    public static void glTexEnvf(int a, int b, float c) {
        GL11.glTexEnvf(a, b, c);
    }

    public static void glTranslated(double a, double b, double c) {
        GL11.glTranslated(a, b, c);
    }

    public static void glVertex2i(int a, int b) {
        GL11.glVertex2i(a, b);
    }

    public static void glTexGenf(int a, int b, float c) {
        GL11.glTexGenf(a, b, c);
    }

    public static void glTexGen(int a, int b, IntBuffer c) {
        GL11.glTexGen(a, b, c);
    }

    public static void glTexGen(int a, int b, FloatBuffer c) {
        GL11.glTexGen(a, b, c);
    }

    public static void glTexGen(int a, int b, DoubleBuffer c) {
        GL11.glTexGen(a, b, c);
    }

    public static void glTexCoord2d(double a, double b) {
        GL11.glTexCoord2d(a, b);
    }

    public static void glStencilFunc(int a, int b, int c) {
        GL11.glStencilFunc(a, b, c);
    }

    public static void glTexCoord3f(float a, float b, float c) {
        GL11.glTexCoord3f(a, b, c);
    }

    public static void glVertex2d(double a, double b) {
        GL11.glVertex2d(a, b);
    }

    public static void glTranslatef(float a, float b, float c) {
        GL11.glTranslatef(a, b, c);
    }

    public static void glTexEnv(int a, int b, FloatBuffer c) {
        GL11.glTexEnv(a, b, c);
    }

    public static void glTexEnv(int a, int b, IntBuffer c) {
        GL11.glTexEnv(a, b, c);
    }

    public static void glTexCoord1f(float a) {
        GL11.glTexCoord1f(a);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, ShortBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, FloatBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, IntBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, DoubleBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, ByteBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, long h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, IntBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, FloatBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, DoubleBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, ByteBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, ShortBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, long j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glCopyTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
        GL12.glCopyTexSubImage3D(a, b, c, d, e, f, g, h, i);
    }

    public static void glDrawRangeElements(int a, int b, int c, ByteBuffer d) {
        GL12.glDrawRangeElements(a, b, c, d);
    }

    public static void glDrawRangeElements(int a, int b, int c, int d, int e, long f) {
        GL12.glDrawRangeElements(a, b, c, d, e, f);
    }

    public static void glDrawRangeElements(int a, int b, int c, IntBuffer d) {
        GL12.glDrawRangeElements(a, b, c, d);
    }

    public static void glDrawRangeElements(int a, int b, int c, ShortBuffer d) {
        GL12.glDrawRangeElements(a, b, c, d);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, long k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, DoubleBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ByteBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, FloatBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ShortBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, IntBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glMultiTexCoord1f(int a, float b) {
        GL13.glMultiTexCoord1f(a, b);
    }

    public static void glClientActiveTexture(int a) {
        GL13.glClientActiveTexture(a);
    }

    public static void glCompressedTexSubImage1D(int a, int b, int c, int d, int e, int f, long g) {
        GL13.glCompressedTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glCompressedTexSubImage1D(int a, int b, int c, int d, int e, ByteBuffer f) {
        GL13.glCompressedTexSubImage1D(a, b, c, d, e, f);
    }

    public static void glCompressedTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, long k) {
        GL13.glCompressedTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glCompressedTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, ByteBuffer j) {
        GL13.glCompressedTexSubImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glGetCompressedTexImage(int a, int b, ShortBuffer c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glGetCompressedTexImage(int a, int b, ByteBuffer c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glGetCompressedTexImage(int a, int b, long c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glGetCompressedTexImage(int a, int b, IntBuffer c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glCompressedTexImage1D(int a, int b, int c, int d, int e, ByteBuffer f) {
        GL13.glCompressedTexImage1D(a, b, c, d, e, f);
    }

    public static void glCompressedTexImage1D(int a, int b, int c, int d, int e, int f, long g) {
        GL13.glCompressedTexImage1D(a, b, c, d, e, f, g);
    }

    public static void glActiveTexture(int a) {
        GL13.glActiveTexture(a);
    }

    public static void glCompressedTexImage2D(int a, int b, int c, int d, int e, int f, int g, long h) {
        GL13.glCompressedTexImage2D(a, b, c, d, e, f, g, h);
    }

    public static void glCompressedTexImage2D(int a, int b, int c, int d, int e, int f, ByteBuffer g) {
        GL13.glCompressedTexImage2D(a, b, c, d, e, f, g);
    }

    public static void glCompressedTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL13.glCompressedTexImage3D(a, b, c, d, e, f, g, h, i);
    }

    public static void glCompressedTexImage3D(int a, int b, int c, int d, int e, int f, int g, ByteBuffer h) {
        GL13.glCompressedTexImage3D(a, b, c, d, e, f, g, h);
    }

    public static void glCompressedTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, ByteBuffer h) {
        GL13.glCompressedTexSubImage2D(a, b, c, d, e, f, g, h);
    }

    public static void glCompressedTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL13.glCompressedTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glMultiTexCoord3d(int a, double b, double c, double d) {
        GL13.glMultiTexCoord3d(a, b, c, d);
    }

    public static void glMultTransposeMatrix(DoubleBuffer a) {
        GL13.glMultTransposeMatrix(a);
    }

    public static void glMultTransposeMatrix(FloatBuffer a) {
        GL13.glMultTransposeMatrix(a);
    }

    public static void glMultiTexCoord4f(int a, float b, float c, float d, float e) {
        GL13.glMultiTexCoord4f(a, b, c, d, e);
    }

    public static void glMultiTexCoord1d(int a, double b) {
        GL13.glMultiTexCoord1d(a, b);
    }

    public static void glLoadTransposeMatrix(DoubleBuffer a) {
        GL13.glLoadTransposeMatrix(a);
    }

    public static void glLoadTransposeMatrix(FloatBuffer a) {
        GL13.glLoadTransposeMatrix(a);
    }

    public static void glSampleCoverage(float a, boolean b) {
        GL13.glSampleCoverage(a, b);
    }

    public static void glMultiTexCoord3f(int a, float b, float c, float d) {
        GL13.glMultiTexCoord3f(a, b, c, d);
    }

    public static void glMultiTexCoord2f(int a, float b, float c) {
        GL13.glMultiTexCoord2f(a, b, c);
    }

    public static void glMultiTexCoord2d(int a, double b, double c) {
        GL13.glMultiTexCoord2d(a, b, c);
    }

    public static void glMultiTexCoord4d(int a, double b, double c, double d, double e) {
        GL13.glMultiTexCoord4d(a, b, c, d, e);
    }

    public static void glWindowPos3f(float a, float b, float c) {
        GL14.glWindowPos3f(a, b, c);
    }

    public static void glWindowPos2f(float a, float b) {
        GL14.glWindowPos2f(a, b);
    }

    public static void glWindowPos2i(int a, int b) {
        GL14.glWindowPos2i(a, b);
    }

    public static void glBlendColor(float a, float b, float c, float d) {
        GL14.glBlendColor(a, b, c, d);
    }

    public static void glWindowPos2d(double a, double b) {
        GL14.glWindowPos2d(a, b);
    }

    public static void glWindowPos3d(double a, double b, double c) {
        GL14.glWindowPos3d(a, b, c);
    }

    public static void glFogCoordf(float a) {
        GL14.glFogCoordf(a);
    }

    public static void glWindowPos3i(int a, int b, int c) {
        GL14.glWindowPos3i(a, b, c);
    }

    public static void glFogCoordd(double a) {
        GL14.glFogCoordd(a);
    }

    public static void glFogCoordPointer(int a, DoubleBuffer b) {
        GL14.glFogCoordPointer(a, b);
    }

    public static void glFogCoordPointer(int a, FloatBuffer b) {
        GL14.glFogCoordPointer(a, b);
    }

    public static void glFogCoordPointer(int a, int b, long c) {
        GL14.glFogCoordPointer(a, b, c);
    }

    public static void glPointParameterf(int a, float b) {
        GL14.glPointParameterf(a, b);
    }

    public static void glMultiDrawArrays(int a, IntBuffer b, IntBuffer c) {
        GL14.glMultiDrawArrays(a, b, c);
    }

    public static void glSecondaryColor3f(float a, float b, float c) {
        GL14.glSecondaryColor3f(a, b, c);
    }

    public static void glSecondaryColor3b(byte a, byte b, byte c) {
        GL14.glSecondaryColor3b(a, b, c);
    }

    public static void glSecondaryColor3d(double a, double b, double c) {
        GL14.glSecondaryColor3d(a, b, c);
    }

    public static void glPointParameteri(int a, int b) {
        GL14.glPointParameteri(a, b);
    }

    public static void glBlendFuncSeparate(int a, int b, int c, int d) {
        GL14.glBlendFuncSeparate(a, b, c, d);
    }

    public static void glSecondaryColorPointer(int a, int b, DoubleBuffer c) {
        GL14.glSecondaryColorPointer(a, b, c);
    }

    public static void glSecondaryColorPointer(int a, int b, int c, long d) {
        GL14.glSecondaryColorPointer(a, b, c, d);
    }

    public static void glSecondaryColorPointer(int a, int b, FloatBuffer c) {
        GL14.glSecondaryColorPointer(a, b, c);
    }

    public static void glSecondaryColorPointer(int a, boolean b, int c, ByteBuffer d) {
        GL14.glSecondaryColorPointer(a, b, c, d);
    }

    public static void glBlendEquation(int a) {
        GL14.glBlendEquation(a);
    }

    public static void glPointParameter(int a, FloatBuffer b) {
        GL14.glPointParameter(a, b);
    }

    public static void glPointParameter(int a, IntBuffer b) {
        GL14.glPointParameter(a, b);
    }

    public static void glSecondaryColor3ub(byte a, byte b, byte c) {
        GL14.glSecondaryColor3ub(a, b, c);
    }

    public static void glBindBuffer(int a, int b) {
        GL15.glBindBuffer(a, b);
    }

    public static boolean glIsBuffer(int a) {
        return GL15.glIsBuffer(a);
    }

    public static void glBufferData(int a, DoubleBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, FloatBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, long b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, ByteBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, IntBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, ShortBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static ByteBuffer glMapBuffer(int a, int b, ByteBuffer c) {
        return GL15.glMapBuffer(a, b, c);
    }

    public static ByteBuffer glMapBuffer(int a, int b, long c, ByteBuffer d) {
        return GL15.glMapBuffer(a, b, c, d);
    }

    public static boolean glUnmapBuffer(int a) {
        return GL15.glUnmapBuffer(a);
    }

    public static int glGenQueries() {
        return GL15.glGenQueries();
    }

    public static void glGenQueries(IntBuffer a) {
        GL15.glGenQueries(a);
    }

    public static boolean glIsQuery(int a) {
        return GL15.glIsQuery(a);
    }

    public static int glGenBuffers() {
        return GL15.glGenBuffers();
    }

    public static void glGenBuffers(IntBuffer a) {
        GL15.glGenBuffers(a);
    }

    public static void glBeginQuery(int a, int b) {
        GL15.glBeginQuery(a, b);
    }

    public static void glEndQuery(int a) {
        GL15.glEndQuery(a);
    }

    public static void glGetQuery(int a, int b, IntBuffer c) {
        GL15.glGetQuery(a, b, c);
    }

    public static int glGetQuery(int a, int b) {
        return GL15.glGetQuery(a, b);
    }

    public static int glGetQueryi(int a, int b) {
        return GL15.glGetQueryi(a, b);
    }

    public static void glBufferSubData(int a, long b, ByteBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, ShortBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, DoubleBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, IntBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, FloatBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glDeleteBuffers(IntBuffer a) {
        GL15.glDeleteBuffers(a);
    }

    public static void glDeleteBuffers(int a) {
        GL15.glDeleteBuffers(a);
    }

    public static void glGetBufferParameter(int a, int b, IntBuffer c) {
        GL15.glGetBufferParameter(a, b, c);
    }

    public static int glGetBufferParameteri(int a, int b) {
        return GL15.glGetBufferParameteri(a, b);
    }

    public static ByteBuffer glGetBufferPointer(int a, int b) {
        return GL15.glGetBufferPointer(a, b);
    }

    public static void glDeleteQueries(IntBuffer a) {
        GL15.glDeleteQueries(a);
    }

    public static void glDeleteQueries(int a) {
        GL15.glDeleteQueries(a);
    }

    public static void glGetQueryObject(int a, int b, IntBuffer c) {
        GL15.glGetQueryObject(a, b, c);
    }

    public static int glGetQueryObjecti(int a, int b) {
        return GL15.glGetQueryObjecti(a, b);
    }

    public static void glGetQueryObjectu(int a, int b, IntBuffer c) {
        GL15.glGetQueryObjectu(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, ByteBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, ShortBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, IntBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, FloatBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, DoubleBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static int glGetQueryObjectui(int a, int b) {
        return GL15.glGetQueryObjectui(a, b);
    }

    public static void glStencilMaskSeparate(int a, int b) {
        GL20.glStencilMaskSeparate(a, b);
    }

    public static void glBlendEquationSeparate(int a, int b) {
        GL20.glBlendEquationSeparate(a, b);
    }

    public static void glUniform3(int a, IntBuffer b) {
        GL20.glUniform3(a, b);
    }

    public static void glUniform3(int a, FloatBuffer b) {
        GL20.glUniform3(a, b);
    }

    public static void glUniform1i(int a, int b) {
        GL20.glUniform1i(a, b);
    }

    public static void glUniform1f(int a, float b) {
        GL20.glUniform1f(a, b);
    }

    public static boolean glIsShader(int a) {
        return GL20.glIsShader(a);
    }

    public static boolean glIsProgram(int a) {
        return GL20.glIsProgram(a);
    }

    public static void glUniform2i(int a, int b, int c) {
        GL20.glUniform2i(a, b, c);
    }

    public static void glLinkProgram(int a) {
        GL20.glLinkProgram(a);
    }

    public static void glUniform1(int a, FloatBuffer b) {
        GL20.glUniform1(a, b);
    }

    public static void glUniform1(int a, IntBuffer b) {
        GL20.glUniform1(a, b);
    }

    public static void glUniform4i(int a, int b, int c, int d, int e) {
        GL20.glUniform4i(a, b, c, d, e);
    }

    public static void glUniform4f(int a, float b, float c, float d, float e) {
        GL20.glUniform4f(a, b, c, d, e);
    }

    public static void glUniform2f(int a, float b, float c) {
        GL20.glUniform2f(a, b, c);
    }

    public static void glUniform3f(int a, float b, float c, float d) {
        GL20.glUniform3f(a, b, c, d);
    }

    public static void glUniform3i(int a, int b, int c, int d) {
        GL20.glUniform3i(a, b, c, d);
    }

    public static void glUseProgram(int a) {
        GL20.glUseProgram(a);
    }

    public static void glUniform2(int a, FloatBuffer b) {
        GL20.glUniform2(a, b);
    }

    public static void glUniform2(int a, IntBuffer b) {
        GL20.glUniform2(a, b);
    }

    public static void glUniform4(int a, IntBuffer b) {
        GL20.glUniform4(a, b);
    }

    public static void glUniform4(int a, FloatBuffer b) {
        GL20.glUniform4(a, b);
    }

    public static int glGetProgrami(int a, int b) {
        return GL20.glGetProgrami(a, b);
    }

    public static void glGetShader(int a, int b, IntBuffer c) {
        GL20.glGetShader(a, b, c);
    }

    public static int glGetShader(int a, int b) {
        return GL20.glGetShader(a, b);
    }

    public static int glGetShaderi(int a, int b) {
        return GL20.glGetShaderi(a, b);
    }

    public static void glGetUniform(int a, int b, IntBuffer c) {
        GL20.glGetUniform(a, b, c);
    }

    public static void glGetUniform(int a, int b, FloatBuffer c) {
        GL20.glGetUniform(a, b, c);
    }

    public static void glDrawBuffers(int a) {
        GL20.glDrawBuffers(a);
    }

    public static void glDrawBuffers(IntBuffer a) {
        GL20.glDrawBuffers(a);
    }

    public static void glGetProgram(int a, int b, IntBuffer c) {
        GL20.glGetProgram(a, b, c);
    }

    public static int glGetProgram(int a, int b) {
        return GL20.glGetProgram(a, b);
    }

    public static int glCreateShader(int a) {
        return GL20.glCreateShader(a);
    }

    public static void glShaderSource(int a, CharSequence b) {
        GL20.glShaderSource(a, b);
    }

    public static void glShaderSource(int a, ByteBuffer b) {
        GL20.glShaderSource(a, b);
    }

    public static void glShaderSource(int a, CharSequence[] b) {
        GL20.glShaderSource(a, b);
    }

    public static void glDeleteShader(int a) {
        GL20.glDeleteShader(a);
    }

    public static void glDetachShader(int a, int b) {
        GL20.glDetachShader(a, b);
    }

    public static void glVertexAttrib1d(int a, double b) {
        GL20.glVertexAttrib1d(a, b);
    }

    public static void glGetAttachedShaders(int a, IntBuffer b, IntBuffer c) {
        GL20.glGetAttachedShaders(a, b, c);
    }

    public static void glUniformMatrix3(int a, boolean b, FloatBuffer c) {
        GL20.glUniformMatrix3(a, b, c);
    }

    public static int glGetActiveUniformSize(int a, int b) {
        return GL20.glGetActiveUniformSize(a, b);
    }

    public static void glVertexAttrib1s(int a, short b) {
        GL20.glVertexAttrib1s(a, b);
    }

    public static void glVertexAttrib1f(int a, float b) {
        GL20.glVertexAttrib1f(a, b);
    }

    public static void glVertexAttrib2s(int a, short b, short c) {
        GL20.glVertexAttrib2s(a, b, c);
    }

    public static void glUniformMatrix4(int a, boolean b, FloatBuffer c) {
        GL20.glUniformMatrix4(a, b, c);
    }

    public static void glGetShaderInfoLog(int a, IntBuffer b, ByteBuffer c) {
        GL20.glGetShaderInfoLog(a, b, c);
    }

    public static String glGetShaderInfoLog(int a, int b) {
        return GL20.glGetShaderInfoLog(a, b);
    }

    public static String glGetProgramInfoLog(int a, int b) {
        return GL20.glGetProgramInfoLog(a, b);
    }

    public static void glGetProgramInfoLog(int a, IntBuffer b, ByteBuffer c) {
        GL20.glGetProgramInfoLog(a, b, c);
    }

    public static void glAttachShader(int a, int b) {
        GL20.glAttachShader(a, b);
    }

    public static void glValidateProgram(int a) {
        GL20.glValidateProgram(a);
    }

    public static void glDeleteProgram(int a) {
        GL20.glDeleteProgram(a);
    }

    public static void glGetActiveUniform(int a, int b, IntBuffer c, IntBuffer d, IntBuffer e, ByteBuffer f) {
        GL20.glGetActiveUniform(a, b, c, d, e, f);
    }

    public static String glGetActiveUniform(int a, int b, int c) {
        return GL20.glGetActiveUniform(a, b, c);
    }

    public static String glGetActiveUniform(int a, int b, int c, IntBuffer d) {
        return GL20.glGetActiveUniform(a, b, c, d);
    }

    public static void glGetShaderSource(int a, IntBuffer b, ByteBuffer c) {
        GL20.glGetShaderSource(a, b, c);
    }

    public static String glGetShaderSource(int a, int b) {
        return GL20.glGetShaderSource(a, b);
    }

    public static void glVertexAttrib2f(int a, float b, float c) {
        GL20.glVertexAttrib2f(a, b, c);
    }

    public static void glVertexAttrib2d(int a, double b, double c) {
        GL20.glVertexAttrib2d(a, b, c);
    }

    public static void glVertexAttrib3s(int a, short b, short c, short d) {
        GL20.glVertexAttrib3s(a, b, c, d);
    }

    public static int glGetUniformLocation(int a, ByteBuffer b) {
        return GL20.glGetUniformLocation(a, b);
    }

    public static int glGetUniformLocation(int a, CharSequence b) {
        return GL20.glGetUniformLocation(a, b);
    }

    public static void glUniformMatrix2(int a, boolean b, FloatBuffer c) {
        GL20.glUniformMatrix2(a, b, c);
    }

    public static void glVertexAttrib3f(int a, float b, float c, float d) {
        GL20.glVertexAttrib3f(a, b, c, d);
    }

    public static void glVertexAttrib3d(int a, double b, double c, double d) {
        GL20.glVertexAttrib3d(a, b, c, d);
    }

    public static void glVertexAttrib4s(int a, short b, short c, short d, short e) {
        GL20.glVertexAttrib4s(a, b, c, d, e);
    }

    public static void glCompileShader(int a) {
        GL20.glCompileShader(a);
    }

    public static int glGetActiveUniformType(int a, int b) {
        return GL20.glGetActiveUniformType(a, b);
    }

    public static int glCreateProgram() {
        return GL20.glCreateProgram();
    }

    public static void glVertexAttrib4d(int a, double b, double c, double d, double e) {
        GL20.glVertexAttrib4d(a, b, c, d, e);
    }

    public static void glVertexAttrib4Nub(int a, byte b, byte c, byte d, byte e) {
        GL20.glVertexAttrib4Nub(a, b, c, d, e);
    }

    public static void glGetVertexAttrib(int a, int b, FloatBuffer c) {
        GL20.glGetVertexAttrib(a, b, c);
    }

    public static void glGetVertexAttrib(int a, int b, IntBuffer c) {
        GL20.glGetVertexAttrib(a, b, c);
    }

    public static void glGetVertexAttrib(int a, int b, DoubleBuffer c) {
        GL20.glGetVertexAttrib(a, b, c);
    }

    public static void glBindAttribLocation(int a, int b, CharSequence c) {
        GL20.glBindAttribLocation(a, b, c);
    }

    public static void glBindAttribLocation(int a, int b, ByteBuffer c) {
        GL20.glBindAttribLocation(a, b, c);
    }

    public static int glGetActiveAttribType(int a, int b) {
        return GL20.glGetActiveAttribType(a, b);
    }

    public static void glStencilFuncSeparate(int a, int b, int c, int d) {
        GL20.glStencilFuncSeparate(a, b, c, d);
    }

    public static void glVertexAttribPointer(int a, int b, int c, boolean d, int e, long f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, int d, FloatBuffer e) {
        GL20.glVertexAttribPointer(a, b, c, d, e);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, boolean d, int e, ByteBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, boolean d, int e, IntBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, boolean d, int e, ShortBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, int c, boolean d, int e, ByteBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, int d, DoubleBuffer e) {
        GL20.glVertexAttribPointer(a, b, c, d, e);
    }

    public static void glVertexAttrib4f(int a, float b, float c, float d, float e) {
        GL20.glVertexAttrib4f(a, b, c, d, e);
    }

    public static void glGetVertexAttribPointer(int a, int b, ByteBuffer c) {
        GL20.glGetVertexAttribPointer(a, b, c);
    }

    public static ByteBuffer glGetVertexAttribPointer(int a, int b, long c) {
        return GL20.glGetVertexAttribPointer(a, b, c);
    }

    public static void glDisableVertexAttribArray(int a) {
        GL20.glDisableVertexAttribArray(a);
    }

    public static int glGetActiveAttribSize(int a, int b) {
        return GL20.glGetActiveAttribSize(a, b);
    }

    public static int glGetAttribLocation(int a, ByteBuffer b) {
        return GL20.glGetAttribLocation(a, b);
    }

    public static int glGetAttribLocation(int a, CharSequence b) {
        return GL20.glGetAttribLocation(a, b);
    }

    public static void glEnableVertexAttribArray(int a) {
        GL20.glEnableVertexAttribArray(a);
    }

    public static String glGetActiveAttrib(int a, int b, int c, IntBuffer d) {
        return GL20.glGetActiveAttrib(a, b, c, d);
    }

    public static String glGetActiveAttrib(int a, int b, int c) {
        return GL20.glGetActiveAttrib(a, b, c);
    }

    public static void glGetActiveAttrib(int a, int b, IntBuffer c, IntBuffer d, IntBuffer e, ByteBuffer f) {
        GL20.glGetActiveAttrib(a, b, c, d, e, f);
    }

    public static void glStencilOpSeparate(int a, int b, int c, int d) {
        GL20.glStencilOpSeparate(a, b, c, d);
    }

    public static void glUniformMatrix2x3(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix2x3(a, b, c);
    }

    public static void glUniformMatrix4x2(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix4x2(a, b, c);
    }

    public static void glUniformMatrix3x2(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix3x2(a, b, c);
    }

    public static void glUniformMatrix2x4(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix2x4(a, b, c);
    }

    public static void glUniformMatrix3x4(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix3x4(a, b, c);
    }

    public static void glUniformMatrix4x3(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix4x3(a, b, c);
    }

    public static int glGetFramebufferAttachmentParameteri(int a, int b, int c) {
        return GL30.glGetFramebufferAttachmentParameteri(a, b, c);
    }

    public static int glGetFramebufferAttachmentParameter(int a, int b, int c) {
        return GL30.glGetFramebufferAttachmentParameter(a, b, c);
    }

    public static void glGetFramebufferAttachmentParameter(int a, int b, int c, IntBuffer d) {
        GL30.glGetFramebufferAttachmentParameter(a, b, c, d);
    }

    public static void glRenderbufferStorageMultisample(int a, int b, int c, int d, int e) {
        GL30.glRenderbufferStorageMultisample(a, b, c, d, e);
    }

    public static void glUniform3ui(int a, int b, int c, int d) {
        GL30.glUniform3ui(a, b, c, d);
    }

    public static void glClearBuffer(int a, int b, IntBuffer c) {
        GL30.glClearBuffer(a, b, c);
    }

    public static void glClearBuffer(int a, int b, FloatBuffer c) {
        GL30.glClearBuffer(a, b, c);
    }

    public static String glGetStringi(int a, int b) {
        return GL30.glGetStringi(a, b);
    }

    public static void glUniform1ui(int a, int b) {
        GL30.glUniform1ui(a, b);
    }

    public static void glUniform4ui(int a, int b, int c, int d, int e) {
        GL30.glUniform4ui(a, b, c, d, e);
    }

    public static void glUniform2ui(int a, int b, int c) {
        GL30.glUniform2ui(a, b, c);
    }

    public static void glVertexAttribI1(int a, IntBuffer b) {
        GL30.glVertexAttribI1(a, b);
    }

    public static void glVertexAttribI3(int a, IntBuffer b) {
        GL30.glVertexAttribI3(a, b);
    }

    public static void glVertexAttribI4(int a, ShortBuffer b) {
        GL30.glVertexAttribI4(a, b);
    }

    public static void glVertexAttribI4(int a, IntBuffer b) {
        GL30.glVertexAttribI4(a, b);
    }

    public static void glVertexAttribI4(int a, ByteBuffer b) {
        GL30.glVertexAttribI4(a, b);
    }

    public static void glVertexAttribI3u(int a, IntBuffer b) {
        GL30.glVertexAttribI3u(a, b);
    }

    public static void glVertexAttribI1i(int a, int b) {
        GL30.glVertexAttribI1i(a, b);
    }

    public static void glVertexAttribI4u(int a, IntBuffer b) {
        GL30.glVertexAttribI4u(a, b);
    }

    public static void glVertexAttribI4u(int a, ByteBuffer b) {
        GL30.glVertexAttribI4u(a, b);
    }

    public static void glVertexAttribI4u(int a, ShortBuffer b) {
        GL30.glVertexAttribI4u(a, b);
    }

    public static void glVertexAttribI4ui(int a, int b, int c, int d, int e) {
        GL30.glVertexAttribI4ui(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, IntBuffer e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, ShortBuffer e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, ByteBuffer e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, long e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribI1ui(int a, int b) {
        GL30.glVertexAttribI1ui(a, b);
    }

    public static void glClearBufferfi(int a, int b, float c, int d) {
        GL30.glClearBufferfi(a, b, c, d);
    }

    public static void glVertexAttribI1u(int a, IntBuffer b) {
        GL30.glVertexAttribI1u(a, b);
    }

    public static void glVertexAttribI2i(int a, int b, int c) {
        GL30.glVertexAttribI2i(a, b, c);
    }

    public static void glVertexAttribI2u(int a, IntBuffer b) {
        GL30.glVertexAttribI2u(a, b);
    }

    public static void glVertexAttribI3ui(int a, int b, int c, int d) {
        GL30.glVertexAttribI3ui(a, b, c, d);
    }

    public static void glClearBufferu(int a, int b, IntBuffer c) {
        GL30.glClearBufferu(a, b, c);
    }

    public static void glVertexAttribI4i(int a, int b, int c, int d, int e) {
        GL30.glVertexAttribI4i(a, b, c, d, e);
    }

    public static void glVertexAttribI2(int a, IntBuffer b) {
        GL30.glVertexAttribI2(a, b);
    }

    public static void glVertexAttribI2ui(int a, int b, int c) {
        GL30.glVertexAttribI2ui(a, b, c);
    }

    public static void glVertexAttribI3i(int a, int b, int c, int d) {
        GL30.glVertexAttribI3i(a, b, c, d);
    }

    public static void glBindRenderbuffer(int a, int b) {
        GL30.glBindRenderbuffer(a, b);
    }

    public static int glGetFragDataLocation(int a, ByteBuffer b) {
        return GL30.glGetFragDataLocation(a, b);
    }

    public static int glGetFragDataLocation(int a, CharSequence b) {
        return GL30.glGetFragDataLocation(a, b);
    }

    public static ByteBuffer glMapBufferRange(int a, long b, long c, int d, ByteBuffer e) {
        return GL30.glMapBufferRange(a, b, c, d, e);
    }

    public static void glDeleteRenderbuffers(IntBuffer a) {
        GL30.glDeleteRenderbuffers(a);
    }

    public static void glDeleteRenderbuffers(int a) {
        GL30.glDeleteRenderbuffers(a);
    }

    public static int glCheckFramebufferStatus(int a) {
        return GL30.glCheckFramebufferStatus(a);
    }

    public static void glRenderbufferStorage(int a, int b, int c, int d) {
        GL30.glRenderbufferStorage(a, b, c, d);
    }

    public static int glGetRenderbufferParameter(int a, int b) {
        return GL30.glGetRenderbufferParameter(a, b);
    }

    public static void glGetRenderbufferParameter(int a, int b, IntBuffer c) {
        GL30.glGetRenderbufferParameter(a, b, c);
    }

    public static void glFramebufferTexture1D(int a, int b, int c, int d, int e) {
        GL30.glFramebufferTexture1D(a, b, c, d, e);
    }

    public static int glGetRenderbufferParameteri(int a, int b) {
        return GL30.glGetRenderbufferParameteri(a, b);
    }

    public static void glFramebufferTexture3D(int a, int b, int c, int d, int e, int f) {
        GL30.glFramebufferTexture3D(a, b, c, d, e, f);
    }

    public static void glGenerateMipmap(int a) {
        GL30.glGenerateMipmap(a);
    }

    public static void glGetVertexAttribI(int a, int b, IntBuffer c) {
        GL30.glGetVertexAttribI(a, b, c);
    }

    public static void glGetVertexAttribIu(int a, int b, IntBuffer c) {
        GL30.glGetVertexAttribIu(a, b, c);
    }

    public static void glBindFragDataLocation(int a, int b, ByteBuffer c) {
        GL30.glBindFragDataLocation(a, b, c);
    }

    public static void glBindFragDataLocation(int a, int b, CharSequence c) {
        GL30.glBindFragDataLocation(a, b, c);
    }

    public static boolean glIsRenderbuffer(int a) {
        return GL30.glIsRenderbuffer(a);
    }

    public static int glGenRenderbuffers() {
        return GL30.glGenRenderbuffers();
    }

    public static void glGenRenderbuffers(IntBuffer a) {
        GL30.glGenRenderbuffers(a);
    }

    public static void glFramebufferTexture2D(int a, int b, int c, int d, int e) {
        GL30.glFramebufferTexture2D(a, b, c, d, e);
    }

    public static void glFramebufferRenderbuffer(int a, int b, int c, int d) {
        GL30.glFramebufferRenderbuffer(a, b, c, d);
    }

    public static void glBlitFramebuffer(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j) {
        GL30.glBlitFramebuffer(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexParameterI(int a, int b, IntBuffer c) {
        GL30.glTexParameterI(a, b, c);
    }

    public static void glGenFramebuffers(IntBuffer a) {
        GL30.glGenFramebuffers(a);
    }

    public static int glGenFramebuffers() {
        return GL30.glGenFramebuffers();
    }

    public static void glEndConditionalRender() {
        GL30.glEndConditionalRender();
    }

    public static void glDeleteFramebuffers(int a) {
        GL30.glDeleteFramebuffers(a);
    }

    public static void glDeleteFramebuffers(IntBuffer a) {
        GL30.glDeleteFramebuffers(a);
    }

    public static void glTexParameterIi(int a, int b, int c) {
        GL30.glTexParameterIi(a, b, c);
    }

    public static void glTexParameterIu(int a, int b, IntBuffer c) {
        GL30.glTexParameterIu(a, b, c);
    }

    public static boolean glIsFramebuffer(int a) {
        return GL30.glIsFramebuffer(a);
    }

    public static void glBeginConditionalRender(int a, int b) {
        GL30.glBeginConditionalRender(a, b);
    }

    public static void glBindFramebuffer(int a, int b) {
        GL30.glBindFramebuffer(a, b);
    }

    public static void glTexParameterIui(int a, int b, int c) {
        GL30.glTexParameterIui(a, b, c);
    }

    public static void glFlushMappedBufferRange(int a, long b, long c) {
        GL30.glFlushMappedBufferRange(a, b, c);
    }

    public static void glGetTexParameterI(int a, int b, IntBuffer c) {
        GL30.glGetTexParameterI(a, b, c);
    }

    public static int glGetTexParameterIui(int a, int b) {
        return GL30.glGetTexParameterIui(a, b);
    }

    public static void glDeleteVertexArrays(IntBuffer a) {
        GL30.glDeleteVertexArrays(a);
    }

    public static void glDeleteVertexArrays(int a) {
        GL30.glDeleteVertexArrays(a);
    }

    public static void glBindBufferRange(int a, int b, int c, long d, long e) {
        GL30.glBindBufferRange(a, b, c, d, e);
    }

    public static void glGetTexParameterIu(int a, int b, IntBuffer c) {
        GL30.glGetTexParameterIu(a, b, c);
    }

    public static void glEndTransformFeedback() {
        GL30.glEndTransformFeedback();
    }

    public static boolean glIsVertexArray(int a) {
        return GL30.glIsVertexArray(a);
    }

    public static void glFramebufferTextureLayer(int a, int b, int c, int d, int e) {
        GL30.glFramebufferTextureLayer(a, b, c, d, e);
    }

    public static void glGetTransformFeedbackVarying(int a, int b, IntBuffer c, IntBuffer d, IntBuffer e, ByteBuffer f) {
        GL30.glGetTransformFeedbackVarying(a, b, c, d, e, f);
    }

    public static String glGetTransformFeedbackVarying(int a, int b, int c, IntBuffer d, IntBuffer e) {
        return GL30.glGetTransformFeedbackVarying(a, b, c, d, e);
    }

    public static void glTransformFeedbackVaryings(int a, int b, ByteBuffer c, int d) {
        GL30.glTransformFeedbackVaryings(a, b, c, d);
    }

    public static void glTransformFeedbackVaryings(int a, CharSequence[] b, int c) {
        GL30.glTransformFeedbackVaryings(a, b, c);
    }

    public static void glBeginTransformFeedback(int a) {
        GL30.glBeginTransformFeedback(a);
    }

    public static void glBindVertexArray(int a) {
        GL30.glBindVertexArray(a);
    }

    public static void glBindBufferBase(int a, int b, int c) {
        GL30.glBindBufferBase(a, b, c);
    }

    public static int glGenVertexArrays() {
        return GL30.glGenVertexArrays();
    }

    public static void glGenVertexArrays(IntBuffer a) {
        GL30.glGenVertexArrays(a);
    }

    public static int glGetTexParameterIi(int a, int b) {
        return GL30.glGetTexParameterIi(a, b);
    }

    public static void glUniform3u(int a, IntBuffer b) {
        GL30.glUniform3u(a, b);
    }

    public static void glClampColor(int a, int b) {
        GL30.glClampColor(a, b);
    }

    public static void glGetUniformu(int a, int b, IntBuffer c) {
        GL30.glGetUniformu(a, b, c);
    }

    public static void glUniform2u(int a, IntBuffer b) {
        GL30.glUniform2u(a, b);
    }

    public static void glUniform4u(int a, IntBuffer b) {
        GL30.glUniform4u(a, b);
    }

    public static void glEnablei(int a, int b) {
        GL30.glEnablei(a, b);
    }

    public static boolean glIsEnabledi(int a, int b) {
        return GL30.glIsEnabledi(a, b);
    }

    public static void glColorMaski(int a, boolean b, boolean c, boolean d, boolean e) {
        GL30.glColorMaski(a, b, c, d, e);
    }

    public static void glDisablei(int a, int b) {
        GL30.glDisablei(a, b);
    }

    public static int glGetInteger(int a, int b) {
        return GL30.glGetInteger(a, b);
    }

    public static void glGetInteger(int a, int b, IntBuffer c) {
        GL30.glGetInteger(a, b, c);
    }

    public static void glGetBoolean(int a, int b, ByteBuffer c) {
        GL30.glGetBoolean(a, b, c);
    }

    public static boolean glGetBoolean(int a, int b) {
        return GL30.glGetBoolean(a, b);
    }

    public static void glUniform1u(int a, IntBuffer b) {
        GL30.glUniform1u(a, b);
    }

    public static void glGetUniformIndices(int a, CharSequence[] b, IntBuffer c) {
        GL31.glGetUniformIndices(a, b, c);
    }

    public static void glGetUniformIndices(int a, ByteBuffer b, IntBuffer c) {
        GL31.glGetUniformIndices(a, b, c);
    }

    public static int glGetActiveUniforms(int a, int b, int c) {
        return GL31.glGetActiveUniforms(a, b, c);
    }

    public static void glGetActiveUniforms(int a, IntBuffer b, int c, IntBuffer d) {
        GL31.glGetActiveUniforms(a, b, c, d);
    }

    public static String glGetActiveUniformName(int a, int b, int c) {
        return GL31.glGetActiveUniformName(a, b, c);
    }

    public static void glGetActiveUniformName(int a, int b, IntBuffer c, ByteBuffer d) {
        GL31.glGetActiveUniformName(a, b, c, d);
    }

    public static void glUniformBlockBinding(int a, int b, int c) {
        GL31.glUniformBlockBinding(a, b, c);
    }

    public static void glDrawArraysInstanced(int a, int b, int c, int d) {
        GL31.glDrawArraysInstanced(a, b, c, d);
    }

    public static int glGetActiveUniformsi(int a, int b, int c) {
        return GL31.glGetActiveUniformsi(a, b, c);
    }

    public static String glGetActiveUniformBlockName(int a, int b, int c) {
        return GL31.glGetActiveUniformBlockName(a, b, c);
    }

    public static void glGetActiveUniformBlockName(int a, int b, IntBuffer c, ByteBuffer d) {
        GL31.glGetActiveUniformBlockName(a, b, c, d);
    }

    public static void glDrawElementsInstanced(int a, IntBuffer b, int c) {
        GL31.glDrawElementsInstanced(a, b, c);
    }

    public static void glDrawElementsInstanced(int a, ShortBuffer b, int c) {
        GL31.glDrawElementsInstanced(a, b, c);
    }

    public static void glDrawElementsInstanced(int a, ByteBuffer b, int c) {
        GL31.glDrawElementsInstanced(a, b, c);
    }

    public static void glDrawElementsInstanced(int a, int b, int c, long d, int e) {
        GL31.glDrawElementsInstanced(a, b, c, d, e);
    }

    public static void glPrimitiveRestartIndex(int a) {
        GL31.glPrimitiveRestartIndex(a);
    }

    public static int glGetActiveUniformBlocki(int a, int b, int c) {
        return GL31.glGetActiveUniformBlocki(a, b, c);
    }

    public static int glGetUniformBlockIndex(int a, ByteBuffer b) {
        return GL31.glGetUniformBlockIndex(a, b);
    }

    public static int glGetUniformBlockIndex(int a, CharSequence b) {
        return GL31.glGetUniformBlockIndex(a, b);
    }

    public static void glCopyBufferSubData(int a, int b, long c, long d, long e) {
        GL31.glCopyBufferSubData(a, b, c, d, e);
    }

    public static int glGetActiveUniformBlock(int a, int b, int c) {
        return GL31.glGetActiveUniformBlock(a, b, c);
    }

    public static void glGetActiveUniformBlock(int a, int b, int c, IntBuffer d) {
        GL31.glGetActiveUniformBlock(a, b, c, d);
    }

    public static void glTexBuffer(int a, int b, int c) {
        GL31.glTexBuffer(a, b, c);
    }

    public static long glGetInteger64(int a) {
        return GL32.glGetInteger64(a);
    }

    public static long glGetInteger64(int a, int b) {
        return GL32.glGetInteger64(a, b);
    }

    public static void glGetInteger64(int a, int b, LongBuffer c) {
        GL32.glGetInteger64(a, b, c);
    }

    public static void glGetInteger64(int a, LongBuffer b) {
        GL32.glGetInteger64(a, b);
    }

    public static int glClientWaitSync(GLSync a, int b, long c) {
        return GL32.glClientWaitSync(a, b, c);
    }

    public static void glDrawElementsBaseVertex(int a, int b, int c, long d, int e) {
        GL32.glDrawElementsBaseVertex(a, b, c, d, e);
    }

    public static void glDrawElementsBaseVertex(int a, ByteBuffer b, int c) {
        GL32.glDrawElementsBaseVertex(a, b, c);
    }

    public static void glDrawElementsBaseVertex(int a, ShortBuffer b, int c) {
        GL32.glDrawElementsBaseVertex(a, b, c);
    }

    public static void glDrawElementsBaseVertex(int a, IntBuffer b, int c) {
        GL32.glDrawElementsBaseVertex(a, b, c);
    }

    public static void glTexImage3DMultisample(int a, int b, int c, int d, int e, int f, boolean g) {
        GL32.glTexImage3DMultisample(a, b, c, d, e, f, g);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, int d, int e, long f, int g) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e, f, g);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, ByteBuffer d, int e) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, ShortBuffer d, int e) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, IntBuffer d, int e) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e);
    }

    public static void glProvokingVertex(int a) {
        GL32.glProvokingVertex(a);
    }

    public static void glTexImage2DMultisample(int a, int b, int c, int d, int e, boolean f) {
        GL32.glTexImage2DMultisample(a, b, c, d, e, f);
    }

    public static void glGetMultisample(int a, int b, FloatBuffer c) {
        GL32.glGetMultisample(a, b, c);
    }

    public static void glFramebufferTexture(int a, int b, int c, int d) {
        GL32.glFramebufferTexture(a, b, c, d);
    }

    public static long glGetBufferParameteri64(int a, int b) {
        return GL32.glGetBufferParameteri64(a, b);
    }

    public static void glSampleMaski(int a, int b) {
        GL32.glSampleMaski(a, b);
    }

    public static boolean glIsSync(GLSync a) {
        return GL32.glIsSync(a);
    }

    public static GLSync glFenceSync(int a, int b) {
        return GL32.glFenceSync(a, b);
    }

    public static void glWaitSync(GLSync a, int b, long c) {
        GL32.glWaitSync(a, b, c);
    }

    public static void glDeleteSync(GLSync a) {
        GL32.glDeleteSync(a);
    }

    public static int glGetSynci(GLSync a, int b) {
        return GL32.glGetSynci(a, b);
    }

    public static int glGetSync(GLSync a, int b) {
        return GL32.glGetSync(a, b);
    }

    public static void glGetSync(GLSync a, int b, IntBuffer c, IntBuffer d) {
        GL32.glGetSync(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, int b, int c, long d, int e, int f) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d, e, f);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, IntBuffer b, int c, int d) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, ShortBuffer b, int c, int d) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, ByteBuffer b, int c, int d) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d);
    }

    public static void glGetBufferParameter(int a, int b, LongBuffer c) {
        GL32.glGetBufferParameter(a, b, c);
    }

    public static long glGetBufferParameter(int a, int b) {
        return GL32.glGetBufferParameter(a, b);
    }

    public static void glGetSamplerParameter(int a, int b, FloatBuffer c) {
        GL33.glGetSamplerParameter(a, b, c);
    }

    public static void glGetSamplerParameter(int a, int b, IntBuffer c) {
        GL33.glGetSamplerParameter(a, b, c);
    }

    public static int glGetSamplerParameterIi(int a, int b) {
        return GL33.glGetSamplerParameterIi(a, b);
    }

    public static void glBindFragDataLocationIndexed(int a, int b, int c, CharSequence d) {
        GL33.glBindFragDataLocationIndexed(a, b, c, d);
    }

    public static void glBindFragDataLocationIndexed(int a, int b, int c, ByteBuffer d) {
        GL33.glBindFragDataLocationIndexed(a, b, c, d);
    }

    public static void glSamplerParameteri(int a, int b, int c) {
        GL33.glSamplerParameteri(a, b, c);
    }

    public static void glDeleteSamplers(int a) {
        GL33.glDeleteSamplers(a);
    }

    public static void glDeleteSamplers(IntBuffer a) {
        GL33.glDeleteSamplers(a);
    }

    public static void glSamplerParameter(int a, int b, IntBuffer c) {
        GL33.glSamplerParameter(a, b, c);
    }

    public static void glSamplerParameter(int a, int b, FloatBuffer c) {
        GL33.glSamplerParameter(a, b, c);
    }

    public static void glSamplerParameterI(int a, int b, IntBuffer c) {
        GL33.glSamplerParameterI(a, b, c);
    }

    public static void glSamplerParameterf(int a, int b, float c) {
        GL33.glSamplerParameterf(a, b, c);
    }

    public static void glSamplerParameterIu(int a, int b, IntBuffer c) {
        GL33.glSamplerParameterIu(a, b, c);
    }

    public static float glGetSamplerParameterf(int a, int b) {
        return GL33.glGetSamplerParameterf(a, b);
    }

    public static void glGetSamplerParameterI(int a, int b, IntBuffer c) {
        GL33.glGetSamplerParameterI(a, b, c);
    }

    public static void glGetSamplerParameterIu(int a, int b, IntBuffer c) {
        GL33.glGetSamplerParameterIu(a, b, c);
    }

    public static int glGetSamplerParameterIui(int a, int b) {
        return GL33.glGetSamplerParameterIui(a, b);
    }

    public static int glGetFragDataIndex(int a, CharSequence b) {
        return GL33.glGetFragDataIndex(a, b);
    }

    public static int glGetFragDataIndex(int a, ByteBuffer b) {
        return GL33.glGetFragDataIndex(a, b);
    }

    public static int glGetSamplerParameteri(int a, int b) {
        return GL33.glGetSamplerParameteri(a, b);
    }

    public static void glQueryCounter(int a, int b) {
        GL33.glQueryCounter(a, b);
    }

    public static long glGetQueryObjecti64(int a, int b) {
        return GL33.glGetQueryObjecti64(a, b);
    }

    public static void glVertexAttribP4u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP4u(a, b, c, d);
    }

    public static void glVertexAttribDivisor(int a, int b) {
        GL33.glVertexAttribDivisor(a, b);
    }

    public static void glVertexAttribP3u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP3u(a, b, c, d);
    }

    public static long glGetQueryObjectui64(int a, int b) {
        return GL33.glGetQueryObjectui64(a, b);
    }

    public static void glTexCoordP2ui(int a, int b) {
        GL33.glTexCoordP2ui(a, b);
    }

    public static void glMultiTexCoordP4ui(int a, int b, int c) {
        GL33.glMultiTexCoordP4ui(a, b, c);
    }

    public static void glVertexAttribP2ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP2ui(a, b, c, d);
    }

    public static void glMultiTexCoordP4u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP4u(a, b, c);
    }

    public static void glTexCoordP4ui(int a, int b) {
        GL33.glTexCoordP4ui(a, b);
    }

    public static void glMultiTexCoordP2ui(int a, int b, int c) {
        GL33.glMultiTexCoordP2ui(a, b, c);
    }

    public static void glMultiTexCoordP3ui(int a, int b, int c) {
        GL33.glMultiTexCoordP3ui(a, b, c);
    }

    public static void glTexCoordP3ui(int a, int b) {
        GL33.glTexCoordP3ui(a, b);
    }

    public static void glMultiTexCoordP1u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP1u(a, b, c);
    }

    public static void glMultiTexCoordP2u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP2u(a, b, c);
    }

    public static void glSecondaryColorP3u(int a, IntBuffer b) {
        GL33.glSecondaryColorP3u(a, b);
    }

    public static void glTexCoordP1ui(int a, int b) {
        GL33.glTexCoordP1ui(a, b);
    }

    public static void glMultiTexCoordP3u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP3u(a, b, c);
    }

    public static void glVertexAttribP1ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP1ui(a, b, c, d);
    }

    public static void glVertexAttribP3ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP3ui(a, b, c, d);
    }

    public static void glVertexAttribP4ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP4ui(a, b, c, d);
    }

    public static void glVertexAttribP1u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP1u(a, b, c, d);
    }

    public static void glVertexAttribP2u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP2u(a, b, c, d);
    }

    public static void glMultiTexCoordP1ui(int a, int b, int c) {
        GL33.glMultiTexCoordP1ui(a, b, c);
    }

    public static void glSecondaryColorP3ui(int a, int b) {
        GL33.glSecondaryColorP3ui(a, b);
    }

    public static void glBindSampler(int a, int b) {
        GL33.glBindSampler(a, b);
    }

    public static void glGenSamplers(IntBuffer a) {
        GL33.glGenSamplers(a);
    }

    public static int glGenSamplers() {
        return GL33.glGenSamplers();
    }

    public static void glVertexP2ui(int a, int b) {
        GL33.glVertexP2ui(a, b);
    }

    public static boolean glIsSampler(int a) {
        return GL33.glIsSampler(a);
    }

    public static void glVertexP3ui(int a, int b) {
        GL33.glVertexP3ui(a, b);
    }

    public static void glVertexP3u(int a, IntBuffer b) {
        GL33.glVertexP3u(a, b);
    }

    public static void glColorP4u(int a, IntBuffer b) {
        GL33.glColorP4u(a, b);
    }

    public static void glTexCoordP3u(int a, IntBuffer b) {
        GL33.glTexCoordP3u(a, b);
    }

    public static void glColorP3ui(int a, int b) {
        GL33.glColorP3ui(a, b);
    }

    public static void glTexCoordP4u(int a, IntBuffer b) {
        GL33.glTexCoordP4u(a, b);
    }

    public static void glVertexP2u(int a, IntBuffer b) {
        GL33.glVertexP2u(a, b);
    }

    public static void glNormalP3ui(int a, int b) {
        GL33.glNormalP3ui(a, b);
    }

    public static void glVertexP4u(int a, IntBuffer b) {
        GL33.glVertexP4u(a, b);
    }

    public static void glColorP3u(int a, IntBuffer b) {
        GL33.glColorP3u(a, b);
    }

    public static void glVertexP4ui(int a, int b) {
        GL33.glVertexP4ui(a, b);
    }

    public static void glNormalP3u(int a, IntBuffer b) {
        GL33.glNormalP3u(a, b);
    }

    public static void glTexCoordP1u(int a, IntBuffer b) {
        GL33.glTexCoordP1u(a, b);
    }

    public static void glTexCoordP2u(int a, IntBuffer b) {
        GL33.glTexCoordP2u(a, b);
    }

    public static void glColorP4ui(int a, int b) {
        GL33.glColorP4ui(a, b);
    }

    public static void glGetQueryObject(int a, int b, LongBuffer c) {
        GL33.glGetQueryObject(a, b, c);
    }

    public static long glGetQueryObject(int a, int b) {
        return GL33.glGetQueryObject(a, b);
    }

    public static long glGetQueryObjectu(int a, int b) {
        return GL33.glGetQueryObjectu(a, b);
    }

    public static void glGetQueryObjectu(int a, int b, LongBuffer c) {
        GL33.glGetQueryObjectu(a, b, c);
    }

    public static void glUniformMatrix2x3(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix2x3(a, b, c);
    }

    public static void glUniformMatrix4x2(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix4x2(a, b, c);
    }

    public static void glUniformMatrix3x2(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix3x2(a, b, c);
    }

    public static void glUniformMatrix2x4(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix2x4(a, b, c);
    }

    public static void glUniformMatrix3x4(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix3x4(a, b, c);
    }

    public static void glUniformMatrix4x3(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix4x3(a, b, c);
    }

    public static void glUniform3(int a, DoubleBuffer b) {
        GL40.glUniform3(a, b);
    }

    public static void glUniform1(int a, DoubleBuffer b) {
        GL40.glUniform1(a, b);
    }

    public static void glUniform2(int a, DoubleBuffer b) {
        GL40.glUniform2(a, b);
    }

    public static void glUniform4(int a, DoubleBuffer b) {
        GL40.glUniform4(a, b);
    }

    public static void glGetUniform(int a, int b, DoubleBuffer c) {
        GL40.glGetUniform(a, b, c);
    }

    public static void glDrawArraysIndirect(int a, long b) {
        GL40.glDrawArraysIndirect(a, b);
    }

    public static void glDrawArraysIndirect(int a, ByteBuffer b) {
        GL40.glDrawArraysIndirect(a, b);
    }

    public static void glDrawArraysIndirect(int a, IntBuffer b) {
        GL40.glDrawArraysIndirect(a, b);
    }

    public static void glBlendEquationi(int a, int b) {
        GL40.glBlendEquationi(a, b);
    }

    public static void glBlendFuncSeparatei(int a, int b, int c, int d, int e) {
        GL40.glBlendFuncSeparatei(a, b, c, d, e);
    }

    public static void glDrawElementsIndirect(int a, int b, IntBuffer c) {
        GL40.glDrawElementsIndirect(a, b, c);
    }

    public static void glDrawElementsIndirect(int a, int b, long c) {
        GL40.glDrawElementsIndirect(a, b, c);
    }

    public static void glDrawElementsIndirect(int a, int b, ByteBuffer c) {
        GL40.glDrawElementsIndirect(a, b, c);
    }

    public static void glBlendEquationSeparatei(int a, int b, int c) {
        GL40.glBlendEquationSeparatei(a, b, c);
    }

    public static void glUniformSubroutinesu(int a, IntBuffer b) {
        GL40.glUniformSubroutinesu(a, b);
    }

    public static int glGetUniformSubroutineui(int a, int b) {
        return GL40.glGetUniformSubroutineui(a, b);
    }

    public static void glGetQueryIndexed(int a, int b, int c, IntBuffer d) {
        GL40.glGetQueryIndexed(a, b, c, d);
    }

    public static int glGetQueryIndexed(int a, int b, int c) {
        return GL40.glGetQueryIndexed(a, b, c);
    }

    public static void glBindTransformFeedback(int a, int b) {
        GL40.glBindTransformFeedback(a, b);
    }

    public static void glDeleteTransformFeedbacks(int a) {
        GL40.glDeleteTransformFeedbacks(a);
    }

    public static void glDeleteTransformFeedbacks(IntBuffer a) {
        GL40.glDeleteTransformFeedbacks(a);
    }

    public static void glDrawTransformFeedback(int a, int b) {
        GL40.glDrawTransformFeedback(a, b);
    }

    public static int glGetSubroutineIndex(int a, int b, ByteBuffer c) {
        return GL40.glGetSubroutineIndex(a, b, c);
    }

    public static int glGetSubroutineIndex(int a, int b, CharSequence c) {
        return GL40.glGetSubroutineIndex(a, b, c);
    }

    public static void glPatchParameter(int a, FloatBuffer b) {
        GL40.glPatchParameter(a, b);
    }

    public static String glGetActiveSubroutineName(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineName(a, b, c, d);
    }

    public static void glGetActiveSubroutineName(int a, int b, int c, IntBuffer d, ByteBuffer e) {
        GL40.glGetActiveSubroutineName(a, b, c, d, e);
    }

    public static void glResumeTransformFeedback() {
        GL40.glResumeTransformFeedback();
    }

    public static void glEndQueryIndexed(int a, int b) {
        GL40.glEndQueryIndexed(a, b);
    }

    public static void glGetProgramStage(int a, int b, int c, IntBuffer d) {
        GL40.glGetProgramStage(a, b, c, d);
    }

    public static int glGetProgramStage(int a, int b, int c) {
        return GL40.glGetProgramStage(a, b, c);
    }

    public static void glGetActiveSubroutineUniform(int a, int b, int c, int d, IntBuffer e) {
        GL40.glGetActiveSubroutineUniform(a, b, c, d, e);
    }

    public static int glGetActiveSubroutineUniform(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineUniform(a, b, c, d);
    }

    public static void glGetUniformSubroutineu(int a, int b, IntBuffer c) {
        GL40.glGetUniformSubroutineu(a, b, c);
    }

    public static int glGetUniformSubroutineu(int a, int b) {
        return GL40.glGetUniformSubroutineu(a, b);
    }

    public static void glPatchParameteri(int a, int b) {
        GL40.glPatchParameteri(a, b);
    }

    public static void glPauseTransformFeedback() {
        GL40.glPauseTransformFeedback();
    }

    public static int glGetActiveSubroutineUniformi(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineUniformi(a, b, c, d);
    }

    public static void glGenTransformFeedbacks(IntBuffer a) {
        GL40.glGenTransformFeedbacks(a);
    }

    public static int glGenTransformFeedbacks() {
        return GL40.glGenTransformFeedbacks();
    }

    public static boolean glIsTransformFeedback(int a) {
        return GL40.glIsTransformFeedback(a);
    }

    public static void glDrawTransformFeedbackStream(int a, int b, int c) {
        GL40.glDrawTransformFeedbackStream(a, b, c);
    }

    public static void glBeginQueryIndexed(int a, int b, int c) {
        GL40.glBeginQueryIndexed(a, b, c);
    }

    public static int glGetProgramStagei(int a, int b, int c) {
        return GL40.glGetProgramStagei(a, b, c);
    }

    public static int glGetQueryIndexedi(int a, int b, int c) {
        return GL40.glGetQueryIndexedi(a, b, c);
    }

    public static void glMinSampleShading(float a) {
        GL40.glMinSampleShading(a);
    }

    public static int glGetSubroutineUniformLocation(int a, int b, CharSequence c) {
        return GL40.glGetSubroutineUniformLocation(a, b, c);
    }

    public static int glGetSubroutineUniformLocation(int a, int b, ByteBuffer c) {
        return GL40.glGetSubroutineUniformLocation(a, b, c);
    }

    public static void glGetActiveSubroutineUniformName(int a, int b, int c, IntBuffer d, ByteBuffer e) {
        GL40.glGetActiveSubroutineUniformName(a, b, c, d, e);
    }

    public static String glGetActiveSubroutineUniformName(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineUniformName(a, b, c, d);
    }

    public static void glBlendFunci(int a, int b, int c) {
        GL40.glBlendFunci(a, b, c);
    }

    public static void glUniform1d(int a, double b) {
        GL40.glUniform1d(a, b);
    }

    public static void glUniform2d(int a, double b, double c) {
        GL40.glUniform2d(a, b, c);
    }

    public static void glUniform3d(int a, double b, double c, double d) {
        GL40.glUniform3d(a, b, c, d);
    }

    public static void glUniform4d(int a, double b, double c, double d, double e) {
        GL40.glUniform4d(a, b, c, d, e);
    }

    public static void glUniformMatrix3(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix3(a, b, c);
    }

    public static void glUniformMatrix4(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix4(a, b, c);
    }

    public static void glUniformMatrix2(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix2(a, b, c);
    }

    public static void glReleaseShaderCompiler() {
        GL41.glReleaseShaderCompiler();
    }

    public static void glActiveShaderProgram(int a, int b) {
        GL41.glActiveShaderProgram(a, b);
    }

    public static void glBindProgramPipeline(int a) {
        GL41.glBindProgramPipeline(a);
    }

    public static void glShaderBinary(IntBuffer a, int b, ByteBuffer c) {
        GL41.glShaderBinary(a, b, c);
    }

    public static void glProgramParameteri(int a, int b, int c) {
        GL41.glProgramParameteri(a, b, c);
    }

    public static boolean glIsProgramPipeline(int a) {
        return GL41.glIsProgramPipeline(a);
    }

    public static void glProgramUniform3i(int a, int b, int c, int d, int e) {
        GL41.glProgramUniform3i(a, b, c, d, e);
    }

    public static void glGetProgramPipeline(int a, int b, IntBuffer c) {
        GL41.glGetProgramPipeline(a, b, c);
    }

    public static void glGetProgramBinary(int a, IntBuffer b, IntBuffer c, ByteBuffer d) {
        GL41.glGetProgramBinary(a, b, c, d);
    }

    public static void glProgramBinary(int a, int b, ByteBuffer c) {
        GL41.glProgramBinary(a, b, c);
    }

    public static void glUseProgramStages(int a, int b, int c) {
        GL41.glUseProgramStages(a, b, c);
    }

    public static void glGetShaderPrecisionFormat(int a, int b, IntBuffer c, IntBuffer d) {
        GL41.glGetShaderPrecisionFormat(a, b, c, d);
    }

    public static int glCreateShaderProgram(int a, CharSequence b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, ByteBuffer[] b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, ByteBuffer b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, CharSequence[] b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, int b, ByteBuffer c) {
        return GL41.glCreateShaderProgram(a, b, c);
    }

    public static void glDeleteProgramPipelines(int a) {
        GL41.glDeleteProgramPipelines(a);
    }

    public static void glDeleteProgramPipelines(IntBuffer a) {
        GL41.glDeleteProgramPipelines(a);
    }

    public static void glGenProgramPipelines(IntBuffer a) {
        GL41.glGenProgramPipelines(a);
    }

    public static int glGenProgramPipelines() {
        return GL41.glGenProgramPipelines();
    }

    public static void glProgramUniform1i(int a, int b, int c) {
        GL41.glProgramUniform1i(a, b, c);
    }

    public static int glGetProgramPipelinei(int a, int b) {
        return GL41.glGetProgramPipelinei(a, b);
    }

    public static void glProgramUniform2i(int a, int b, int c, int d) {
        GL41.glProgramUniform2i(a, b, c, d);
    }

    public static void glProgramUniform4i(int a, int b, int c, int d, int e, int f) {
        GL41.glProgramUniform4i(a, b, c, d, e, f);
    }

    public static void glDepthRangef(float a, float b) {
        GL41.glDepthRangef(a, b);
    }

    public static void glClearDepthf(float a) {
        GL41.glClearDepthf(a);
    }

    public static float glGetFloat(int a, int b) {
        return GL41.glGetFloat(a, b);
    }

    public static void glGetFloat(int a, int b, FloatBuffer c) {
        GL41.glGetFloat(a, b, c);
    }

    public static void glGetDouble(int a, int b, DoubleBuffer c) {
        GL41.glGetDouble(a, b, c);
    }

    public static double glGetDouble(int a, int b) {
        return GL41.glGetDouble(a, b);
    }

    public static void glProgramUniform4(int a, int b, IntBuffer c) {
        GL41.glProgramUniform4(a, b, c);
    }

    public static void glProgramUniform4(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform4(a, b, c);
    }

    public static void glProgramUniform4(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform4(a, b, c);
    }

    public static void glProgramUniform2ui(int a, int b, int c, int d) {
        GL41.glProgramUniform2ui(a, b, c, d);
    }

    public static void glProgramUniform2d(int a, int b, double c, double d) {
        GL41.glProgramUniform2d(a, b, c, d);
    }

    public static void glProgramUniform4d(int a, int b, double c, double d, double e, double f) {
        GL41.glProgramUniform4d(a, b, c, d, e, f);
    }

    public static void glProgramUniform1ui(int a, int b, int c) {
        GL41.glProgramUniform1ui(a, b, c);
    }

    public static void glProgramUniform1u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform1u(a, b, c);
    }

    public static void glProgramUniformMatrix3(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix3(a, b, c, d);
    }

    public static void glProgramUniformMatrix3(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix3(a, b, c, d);
    }

    public static void glProgramUniform2u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform2u(a, b, c);
    }

    public static void glProgramUniformMatrix2x3(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix2x3(a, b, c, d);
    }

    public static void glProgramUniformMatrix2x3(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix2x3(a, b, c, d);
    }

    public static void glProgramUniform2(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform2(a, b, c);
    }

    public static void glProgramUniform2(int a, int b, IntBuffer c) {
        GL41.glProgramUniform2(a, b, c);
    }

    public static void glProgramUniform2(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform2(a, b, c);
    }

    public static void glProgramUniform1d(int a, int b, double c) {
        GL41.glProgramUniform1d(a, b, c);
    }

    public static void glProgramUniform4ui(int a, int b, int c, int d, int e, int f) {
        GL41.glProgramUniform4ui(a, b, c, d, e, f);
    }

    public static void glProgramUniform1f(int a, int b, float c) {
        GL41.glProgramUniform1f(a, b, c);
    }

    public static void glProgramUniform1(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform1(a, b, c);
    }

    public static void glProgramUniform1(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform1(a, b, c);
    }

    public static void glProgramUniform1(int a, int b, IntBuffer c) {
        GL41.glProgramUniform1(a, b, c);
    }

    public static void glProgramUniformMatrix3x2(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix3x2(a, b, c, d);
    }

    public static void glProgramUniformMatrix3x2(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix3x2(a, b, c, d);
    }

    public static void glProgramUniform3d(int a, int b, double c, double d, double e) {
        GL41.glProgramUniform3d(a, b, c, d, e);
    }

    public static void glProgramUniform3ui(int a, int b, int c, int d, int e) {
        GL41.glProgramUniform3ui(a, b, c, d, e);
    }

    public static void glProgramUniformMatrix2(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix2(a, b, c, d);
    }

    public static void glProgramUniformMatrix2(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix2(a, b, c, d);
    }

    public static void glProgramUniform3u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform3u(a, b, c);
    }

    public static void glProgramUniform4u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform4u(a, b, c);
    }

    public static void glProgramUniformMatrix4(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix4(a, b, c, d);
    }

    public static void glProgramUniformMatrix4(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix4(a, b, c, d);
    }

    public static void glProgramUniform2f(int a, int b, float c, float d) {
        GL41.glProgramUniform2f(a, b, c, d);
    }

    public static void glProgramUniform3f(int a, int b, float c, float d, float e) {
        GL41.glProgramUniform3f(a, b, c, d, e);
    }

    public static void glProgramUniform4f(int a, int b, float c, float d, float e, float f) {
        GL41.glProgramUniform4f(a, b, c, d, e, f);
    }

    public static void glProgramUniform3(int a, int b, IntBuffer c) {
        GL41.glProgramUniform3(a, b, c);
    }

    public static void glProgramUniform3(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform3(a, b, c);
    }

    public static void glProgramUniform3(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform3(a, b, c);
    }

    public static void glVertexAttribL3d(int a, double b, double c, double d) {
        GL41.glVertexAttribL3d(a, b, c, d);
    }

    public static void glVertexAttribL1d(int a, double b) {
        GL41.glVertexAttribL1d(a, b);
    }

    public static void glVertexAttribL2(int a, DoubleBuffer b) {
        GL41.glVertexAttribL2(a, b);
    }

    public static void glGetVertexAttribL(int a, int b, DoubleBuffer c) {
        GL41.glGetVertexAttribL(a, b, c);
    }

    public static void glProgramUniformMatrix2x4(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix2x4(a, b, c, d);
    }

    public static void glProgramUniformMatrix2x4(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix2x4(a, b, c, d);
    }

    public static void glVertexAttribL3(int a, DoubleBuffer b) {
        GL41.glVertexAttribL3(a, b);
    }

    public static void glScissorArray(int a, IntBuffer b) {
        GL41.glScissorArray(a, b);
    }

    public static void glDepthRangeIndexed(int a, double b, double c) {
        GL41.glDepthRangeIndexed(a, b, c);
    }

    public static void glViewportArray(int a, FloatBuffer b) {
        GL41.glViewportArray(a, b);
    }

    public static void glVertexAttribL4d(int a, double b, double c, double d, double e) {
        GL41.glVertexAttribL4d(a, b, c, d, e);
    }

    public static void glGetProgramPipelineInfoLog(int a, IntBuffer b, ByteBuffer c) {
        GL41.glGetProgramPipelineInfoLog(a, b, c);
    }

    public static String glGetProgramPipelineInfoLog(int a, int b) {
        return GL41.glGetProgramPipelineInfoLog(a, b);
    }

    public static void glProgramUniformMatrix4x2(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix4x2(a, b, c, d);
    }

    public static void glProgramUniformMatrix4x2(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix4x2(a, b, c, d);
    }

    public static void glVertexAttribL1(int a, DoubleBuffer b) {
        GL41.glVertexAttribL1(a, b);
    }

    public static void glViewportIndexed(int a, FloatBuffer b) {
        GL41.glViewportIndexed(a, b);
    }

    public static void glVertexAttribL4(int a, DoubleBuffer b) {
        GL41.glVertexAttribL4(a, b);
    }

    public static void glScissorIndexed(int a, IntBuffer b) {
        GL41.glScissorIndexed(a, b);
    }

    public static void glScissorIndexed(int a, int b, int c, int d, int e) {
        GL41.glScissorIndexed(a, b, c, d, e);
    }

    public static void glVertexAttribLPointer(int a, int b, int c, long d) {
        GL41.glVertexAttribLPointer(a, b, c, d);
    }

    public static void glVertexAttribLPointer(int a, int b, int c, DoubleBuffer d) {
        GL41.glVertexAttribLPointer(a, b, c, d);
    }

    public static void glProgramUniformMatrix4x3(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix4x3(a, b, c, d);
    }

    public static void glProgramUniformMatrix4x3(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix4x3(a, b, c, d);
    }

    public static void glViewportIndexedf(int a, float b, float c, float d, float e) {
        GL41.glViewportIndexedf(a, b, c, d, e);
    }

    public static void glDepthRangeArray(int a, DoubleBuffer b) {
        GL41.glDepthRangeArray(a, b);
    }

    public static void glValidateProgramPipeline(int a) {
        GL41.glValidateProgramPipeline(a);
    }

    public static void glVertexAttribL2d(int a, double b, double c) {
        GL41.glVertexAttribL2d(a, b, c);
    }

    public static void glProgramUniformMatrix3x4(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix3x4(a, b, c, d);
    }

    public static void glProgramUniformMatrix3x4(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix3x4(a, b, c, d);
    }

    public static void glMemoryBarrier(int a) {
        GL42.glMemoryBarrier(a);
    }

    public static void glTexStorage3D(int a, int b, int c, int d, int e, int f) {
        GL42.glTexStorage3D(a, b, c, d, e, f);
    }

    public static void glGetInternalformat(int a, int b, int c, IntBuffer d) {
        GL42.glGetInternalformat(a, b, c, d);
    }

    public static int glGetInternalformat(int a, int b, int c) {
        return GL42.glGetInternalformat(a, b, c);
    }

    public static void glTexStorage2D(int a, int b, int c, int d, int e) {
        GL42.glTexStorage2D(a, b, c, d, e);
    }

    public static void glBindImageTexture(int a, int b, int c, boolean d, int e, int f, int g) {
        GL42.glBindImageTexture(a, b, c, d, e, f, g);
    }

    public static void glTexStorage1D(int a, int b, int c, int d) {
        GL42.glTexStorage1D(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, int b, int c, long d, int e, int f, int g) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e, f, g);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, IntBuffer b, int c, int d, int e) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, ByteBuffer b, int c, int d, int e) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, ShortBuffer b, int c, int d, int e) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e);
    }

    public static void glDrawArraysInstancedBaseInstance(int a, int b, int c, int d, int e) {
        GL42.glDrawArraysInstancedBaseInstance(a, b, c, d, e);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, ShortBuffer b, int c, int d) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, ByteBuffer b, int c, int d) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, int b, int c, long d, int e, int f) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d, e, f);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, IntBuffer b, int c, int d) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d);
    }

    public static int glGetActiveAtomicCounterBuffer(int a, int b, int c) {
        return GL42.glGetActiveAtomicCounterBuffer(a, b, c);
    }

    public static void glGetActiveAtomicCounterBuffer(int a, int b, int c, IntBuffer d) {
        GL42.glGetActiveAtomicCounterBuffer(a, b, c, d);
    }

    public static void glDrawTransformFeedbackInstanced(int a, int b, int c) {
        GL42.glDrawTransformFeedbackInstanced(a, b, c);
    }

    public static void glDrawTransformFeedbackStreamInstanced(int a, int b, int c, int d) {
        GL42.glDrawTransformFeedbackStreamInstanced(a, b, c, d);
    }

    public static void glTextureView(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL43.glTextureView(a, b, c, d, e, f, g, h);
    }

    public static void glObjectLabel(int a, int b, ByteBuffer c) {
        KHRDebug.glObjectLabel(a, b, c);
    }

    public static void glObjectLabel(int a, int b, CharSequence c) {
        KHRDebug.glObjectLabel(a, b, c);
    }

    public static String glGetProgramResourceName(int a, int b, int c, int d) {
        return GL43.glGetProgramResourceName(a, b, c, d);
    }

    public static void glGetProgramResourceName(int a, int b, int c, IntBuffer d, ByteBuffer e) {
        GL43.glGetProgramResourceName(a, b, c, d, e);
    }

    public static int glGetDebugMessageLog(int a, IntBuffer b, IntBuffer c, IntBuffer d, IntBuffer e, IntBuffer f, ByteBuffer g) {
        return KHRDebug.glGetDebugMessageLog(a, b, c, d, e, f, g);
    }

    public static void glDebugMessageControl(int a, int b, int c, IntBuffer d, boolean e) {
        KHRDebug.glDebugMessageControl(a, b, c, d, e);
    }

    public static void glDebugMessageCallback(KHRDebugCallback a) {
        KHRDebug.glDebugMessageCallback(a);
    }

    public static void glGetFramebufferParameter(int a, int b, IntBuffer c) {
        GL43.glGetFramebufferParameter(a, b, c);
    }

    public static String glGetObjectPtrLabel(PointerWrapper a, int b) {
        return KHRDebug.glGetObjectPtrLabel(a, b);
    }

    public static void glGetObjectPtrLabel(PointerWrapper a, IntBuffer b, ByteBuffer c) {
        KHRDebug.glGetObjectPtrLabel(a, b, c);
    }

    public static void glInvalidateTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL43.glInvalidateTexSubImage(a, b, c, d, e, f, g, h);
    }

    public static void glGetProgramResource(int a, int b, int c, IntBuffer d, IntBuffer e, IntBuffer f) {
        GL43.glGetProgramResource(a, b, c, d, e, f);
    }

    public static void glCopyImageSubData(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, int m, int n, int o) {
        GL43.glCopyImageSubData(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o);
    }

    public static int glGetProgramResourceLocation(int a, int b, ByteBuffer c) {
        return GL43.glGetProgramResourceLocation(a, b, c);
    }

    public static int glGetProgramResourceLocation(int a, int b, CharSequence c) {
        return GL43.glGetProgramResourceLocation(a, b, c);
    }

    public static void glPopDebugGroup() {
        KHRDebug.glPopDebugGroup();
    }

    public static void glDebugMessageInsert(int a, int b, int c, int d, ByteBuffer e) {
        KHRDebug.glDebugMessageInsert(a, b, c, d, e);
    }

    public static void glDebugMessageInsert(int a, int b, int c, int d, CharSequence e) {
        KHRDebug.glDebugMessageInsert(a, b, c, d, e);
    }

    public static long glGetInternalformati64(int a, int b, int c) {
        return GL43.glGetInternalformati64(a, b, c);
    }

    public static void glFramebufferParameteri(int a, int b, int c) {
        GL43.glFramebufferParameteri(a, b, c);
    }

    public static void glInvalidateBufferData(int a) {
        GL43.glInvalidateBufferData(a);
    }

    public static void glInvalidateFramebuffer(int a, IntBuffer b) {
        GL43.glInvalidateFramebuffer(a, b);
    }

    public static void glInvalidateTexImage(int a, int b) {
        GL43.glInvalidateTexImage(a, b);
    }

    public static void glMultiDrawElementsIndirect(int a, int b, ByteBuffer c, int d, int e) {
        GL43.glMultiDrawElementsIndirect(a, b, c, d, e);
    }

    public static void glMultiDrawElementsIndirect(int a, int b, long c, int d, int e) {
        GL43.glMultiDrawElementsIndirect(a, b, c, d, e);
    }

    public static void glMultiDrawElementsIndirect(int a, int b, IntBuffer c, int d, int e) {
        GL43.glMultiDrawElementsIndirect(a, b, c, d, e);
    }

    public static int glGetProgramInterfacei(int a, int b, int c) {
        return GL43.glGetProgramInterfacei(a, b, c);
    }

    public static void glGetProgramInterface(int a, int b, int c, IntBuffer d) {
        GL43.glGetProgramInterface(a, b, c, d);
    }

    public static void glPushDebugGroup(int a, int b, ByteBuffer c) {
        KHRDebug.glPushDebugGroup(a, b, c);
    }

    public static void glPushDebugGroup(int a, int b, CharSequence c) {
        KHRDebug.glPushDebugGroup(a, b, c);
    }

    public static String glGetObjectLabel(int a, int b, int c) {
        return KHRDebug.glGetObjectLabel(a, b, c);
    }

    public static void glGetObjectLabel(int a, int b, IntBuffer c, ByteBuffer d) {
        KHRDebug.glGetObjectLabel(a, b, c, d);
    }

    public static int glGetFramebufferParameteri(int a, int b) {
        return GL43.glGetFramebufferParameteri(a, b);
    }

    public static void glInvalidateBufferSubData(int a, long b, long c) {
        GL43.glInvalidateBufferSubData(a, b, c);
    }

    public static void glMultiDrawArraysIndirect(int a, ByteBuffer b, int c, int d) {
        GL43.glMultiDrawArraysIndirect(a, b, c, d);
    }

    public static void glMultiDrawArraysIndirect(int a, long b, int c, int d) {
        GL43.glMultiDrawArraysIndirect(a, b, c, d);
    }

    public static void glMultiDrawArraysIndirect(int a, IntBuffer b, int c, int d) {
        GL43.glMultiDrawArraysIndirect(a, b, c, d);
    }

    public static void glInvalidateSubFramebuffer(int a, IntBuffer b, int c, int d, int e, int f) {
        GL43.glInvalidateSubFramebuffer(a, b, c, d, e, f);
    }

    public static int glGetProgramResourceIndex(int a, int b, CharSequence c) {
        return GL43.glGetProgramResourceIndex(a, b, c);
    }

    public static int glGetProgramResourceIndex(int a, int b, ByteBuffer c) {
        return GL43.glGetProgramResourceIndex(a, b, c);
    }

    public static void glObjectPtrLabel(PointerWrapper a, CharSequence b) {
        KHRDebug.glObjectPtrLabel(a, b);
    }

    public static void glObjectPtrLabel(PointerWrapper a, ByteBuffer b) {
        KHRDebug.glObjectPtrLabel(a, b);
    }

    public static void glVertexAttribIFormat(int a, int b, int c, int d) {
        GL43.glVertexAttribIFormat(a, b, c, d);
    }

    public static void glTexStorage2DMultisample(int a, int b, int c, int d, int e, boolean f) {
        GL43.glTexStorage2DMultisample(a, b, c, d, e, f);
    }

    public static void glTexStorage3DMultisample(int a, int b, int c, int d, int e, int f, boolean g) {
        GL43.glTexStorage3DMultisample(a, b, c, d, e, f, g);
    }

    public static void glVertexAttribBinding(int a, int b) {
        GL43.glVertexAttribBinding(a, b);
    }

    public static void glTexBufferRange(int a, int b, int c, long d, long e) {
        GL43.glTexBufferRange(a, b, c, d, e);
    }

    public static void glVertexAttribLFormat(int a, int b, int c, int d) {
        GL43.glVertexAttribLFormat(a, b, c, d);
    }

    public static void glBindVertexBuffer(int a, int b, long c, int d) {
        GL43.glBindVertexBuffer(a, b, c, d);
    }

    public static void glVertexAttribFormat(int a, int b, int c, boolean d, int e) {
        GL43.glVertexAttribFormat(a, b, c, d, e);
    }

    public static void glShaderStorageBlockBinding(int a, int b, int c) {
        GL43.glShaderStorageBlockBinding(a, b, c);
    }

    public static void glVertexBindingDivisor(int a, int b) {
        GL43.glVertexBindingDivisor(a, b);
    }

    public static void glClearBufferSubData(int a, int b, long c, int d, int e, ByteBuffer f) {
        GL43.glClearBufferSubData(a, b, c, d, e, f);
    }

    public static void glDispatchCompute(int a, int b, int c) {
        GL43.glDispatchCompute(a, b, c);
    }

    public static void glDispatchComputeIndirect(long a) {
        GL43.glDispatchComputeIndirect(a);
    }

    public static void glClearBufferData(int a, int b, int c, int d, ByteBuffer e) {
        GL43.glClearBufferData(a, b, c, d, e);
    }

    public static void glGetInternalformat(int a, int b, int c, LongBuffer d) {
        GL43.glGetInternalformat(a, b, c, d);
    }

    public static int glGetProgramResourceLocationIndex(int a, int b, ByteBuffer c) {
        return GL43.glGetProgramResourceLocationIndex(a, b, c);
    }

    public static int glGetProgramResourceLocationIndex(int a, int b, CharSequence c) {
        return GL43.glGetProgramResourceLocationIndex(a, b, c);
    }

    public static void glBindTextures(int a, int b, IntBuffer c) {
        GL44.glBindTextures(a, b, c);
    }

    public static void glClearTexImage(int a, int b, int c, int d, ByteBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, DoubleBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, FloatBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, IntBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, ShortBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, LongBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glBufferStorage(int a, FloatBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, DoubleBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, ByteBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, long b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, LongBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, ShortBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, IntBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBindBuffersBase(int a, int b, int c, IntBuffer d) {
        GL44.glBindBuffersBase(a, b, c, d);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, LongBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ShortBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ByteBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, DoubleBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, IntBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, FloatBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glBindBuffersRange(int a, int b, int c, IntBuffer d, PointerBuffer e, PointerBuffer f) {
        GL44.glBindBuffersRange(a, b, c, d, e, f);
    }

    public static void glBindVertexBuffers(int a, int b, IntBuffer c, PointerBuffer d, IntBuffer e) {
        GL44.glBindVertexBuffers(a, b, c, d, e);
    }

    public static void glBindImageTextures(int a, int b, IntBuffer c) {
        GL44.glBindImageTextures(a, b, c);
    }

    public static void glBindSamplers(int a, int b, IntBuffer c) {
        GL44.glBindSamplers(a, b, c);
    }
}
