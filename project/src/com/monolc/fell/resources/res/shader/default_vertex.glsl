#version 410 core

layout(location = 0)in vec2 position;
layout(location = 1)in vec3 color;

out vec3 vertexColor;

uniform int width;
uniform int height;
uniform float z;
uniform float x;
uniform float y;

void main() {
    vertexColor = color;
    gl_Position = vec4(((position + vec2(x,y)) / (vec2(width,-height) / 2.0)) - vec2(1.0, -1.0), z, 1.0);
}