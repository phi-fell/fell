#version 410 core

in vec3 vertexColor;
in vec2 textureCoord;
in vec2 posCoord;

out vec4 fragColor;

uniform sampler2D texImage;

void main() {
    vec4 textureColor = texture(texImage, textureCoord);
    float lc = 1 -(sqrt(length(posCoord)) / 20);
    if (lc < 0) {
        lc = 0;
    }
    fragColor = (vec4(vertexColor, 1.0) * textureColor) * vec4(lc, lc, lc, 1);
}
