#version 120

varying vec2 texCoord0;

uniform sampler2D sampler;

void main()
{
	vec4 textureColor = texture2D(sampler, texCoord0.xy);
	if(textureColor.w != 0)	// is alpha value not zero?
		gl_FragColor = textureColor;
	else
		discard;
}