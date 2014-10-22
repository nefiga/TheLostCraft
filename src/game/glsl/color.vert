#version 330 core

layout(location = 0) in vec2 pos;
layout(location = 1) in vec4 color;

out vec4 outColor;

void main() {
    outColor = color;

    gl_Position = vec4(pos.x, pos.y, 0.0, 1.0);
}