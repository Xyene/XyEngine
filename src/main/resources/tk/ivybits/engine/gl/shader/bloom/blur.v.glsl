#version 110

varying vec2 v_UV;
varying vec2 v_blurUV[14];

void main()
{
    gl_Position = ftransform();
    v_UV = gl_MultiTexCoord0.xy;
    #ifdef VERTICAL
    v_blurUV[0] = v_UV + vec2(0.0, -0.028);
    v_blurUV[1] = v_UV + vec2(0.0, -0.024);
    v_blurUV[2] = v_UV + vec2(0.0, -0.020);
    v_blurUV[3] = v_UV + vec2(0.0, -0.016);
    v_blurUV[4] = v_UV + vec2(0.0, -0.012);
    v_blurUV[5] = v_UV + vec2(0.0, -0.008);
    v_blurUV[6] = v_UV + vec2(0.0, -0.004);
    v_blurUV[7] = v_UV + vec2(0.0, 0.004);
    v_blurUV[8] = v_UV + vec2(0.0, 0.008);
    v_blurUV[9] = v_UV + vec2(0.0, 0.012);
    v_blurUV[10] = v_UV + vec2(0.0, 0.016);
    v_blurUV[11] = v_UV + vec2(0.0, 0.020);
    v_blurUV[12] = v_UV + vec2(0.0, 0.024);
    v_blurUV[13] = v_UV + vec2(0.0, 0.028);
    #endif
    #ifdef HORIZONTAL
    v_blurUV[0] = v_UV + vec2(-0.028, 0.0);
    v_blurUV[1] = v_UV + vec2(-0.024, 0.0);
    v_blurUV[2] = v_UV + vec2(-0.020, 0.0);
    v_blurUV[3] = v_UV + vec2(-0.016, 0.0);
    v_blurUV[4] = v_UV + vec2(-0.012, 0.0);
    v_blurUV[5] = v_UV + vec2(-0.008, 0.0);
    v_blurUV[6] = v_UV + vec2(-0.004, 0.0);
    v_blurUV[7] = v_UV + vec2(0.004, 0.0);
    v_blurUV[8] = v_UV + vec2(0.008, 0.0);
    v_blurUV[9] = v_UV + vec2(0.012, 0.0);
    v_blurUV[10] = v_UV + vec2(0.016, 0.0);
    v_blurUV[11] = v_UV + vec2(0.020, 0.0);
    v_blurUV[12] = v_UV + vec2(0.024, 0.0);
    v_blurUV[13] = v_UV + vec2(0.028, 0.0);
    #endif
}