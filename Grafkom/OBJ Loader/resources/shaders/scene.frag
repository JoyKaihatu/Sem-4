#version 330
out vec4 frag_color;
uniform vec4 uni_color;
// in -> mendapat data dari luar frag
// out -> mengeluarkan data dari frag
// tidak bisa pakai code ini di frag --> layout (location=0) in vec3 position;
uniform vec3 lightColor;
uniform vec3 lightPos;

in vec3 Normal;
in vec3 FragPos;
void main() {
    // Ambient Strength
    float ambientStrength = 0.5f;
    vec3 ambient = ambientStrength * lightColor;
    // Diffuse
    vec3 lightDirection = normalize(lightPos - FragPos);
    float diff = max(dot(Normal, lightDirection),0.1f);
    vec3 diffuse = diff * lightColor;
    vec3 result = (ambient+diffuse) * vec3(uni_color);
    //frag_color = vec4(1.0, 0.0, 0.0, 1.0); --> Param vec4(red,green,blue,alpha)
    frag_color = vec4(result,1.0);
}