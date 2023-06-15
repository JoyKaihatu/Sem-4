#version 120
struct DirLight{
    vec3 direction;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform DirLight dirLight;
out vec4 fragColor;


void main() {
    fragColor = vec4(result * vec3(uni_color),1.0);
}
