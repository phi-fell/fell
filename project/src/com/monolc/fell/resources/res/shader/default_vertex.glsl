#version 410 core

layout(location = 0)in vec2 position;
layout(location = 1)in vec3 color;
layout(location = 2)in vec2 texcoord;

out vec3 vertexColor;
out vec2 textureCoord;
out vec2 posCoord;

uniform int width;
uniform int height;
uniform float z;
uniform float x;
uniform float y;
uniform float camx;
uniform float camy;
uniform float zoom;

void main() {
    vertexColor = color;
    textureCoord = texcoord;
    gl_Position = vec4(((position + vec2(x,y) - vec2(camx,camy)) / (vec2(width,height) / 2.0)) * zoom, -z, 1.0);
    posCoord = position + vec2(x,y) - vec2(camx,camy);
}