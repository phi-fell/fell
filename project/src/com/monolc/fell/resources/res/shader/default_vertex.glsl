#version 410 core

layout(location = 0)in vec2 position;
layout(location = 1)in vec3 color;
layout(location = 2)in vec2 texcoord;

out vec3 vertexColor;
out vec2 textureCoord;

uniform int width;
uniform int height;
uniform float z;
uniform float x;
uniform float y;

void main() {
    vertexColor = color;
    textureCoord = texcoord;
    gl_Position = vec4(((position + vec2(x,y)) / (vec2(width,height) / 2.0)), z, 1.0);
}