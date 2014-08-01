#version 110

uniform sampler2D u_sampler;

varying vec2 v_UV;
varying vec2 v_blurUV[14];

void main()
{
    gl_FragColor =
    texture2D(u_sampler, v_blurUV[0]) * 0.0044299121055113265
    + texture2D(u_sampler, v_blurUV[1]) * 0.00895781211794
    + texture2D(u_sampler, v_blurUV[2]) * 0.0215963866053
    + texture2D(u_sampler, v_blurUV[3]) * 0.0443683338718
    + texture2D(u_sampler, v_blurUV[4]) * 0.0776744219933
    + texture2D(u_sampler, v_blurUV[5]) * 0.115876621105
    + texture2D(u_sampler, v_blurUV[6]) * 0.147308056121
    + texture2D(u_sampler, v_UV) * 0.159576912161
    + texture2D(u_sampler, v_blurUV[7]) * 0.147308056121
    + texture2D(u_sampler, v_blurUV[8]) * 0.115876621105
    + texture2D(u_sampler, v_blurUV[9]) * 0.0776744219933
    + texture2D(u_sampler, v_blurUV[10]) * 0.0443683338718
    + texture2D(u_sampler, v_blurUV[11]) * 0.0215963866053
    + texture2D(u_sampler, v_blurUV[12]) * 0.00895781211794
    + texture2D(u_sampler, v_blurUV[13]) * 0.0044299121055113265;
}