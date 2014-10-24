package game.graphics;

import game.GameLoop;
import game.util.FileUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {

    private int program, vertexShader, fragmentShader;

    public static final String NORMAL_TEXTURE = "game/glsl/texture";

    public ShaderManager() {
        program = glCreateProgram();
    }

    /**
     * Attach a vertex shader to this program
     * @param name The file name of the vertex shader
     */
    public void attachVertexShader(String name) {
        // Load source
        String vertexShaderSource = FileUtil.readFromFile(name);

        // Create shader and set the source
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);

        glCompileShader(vertexShader);

        // Check for errors
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Unable to create vertex shader");
            System.err.println(glGetShaderInfoLog(vertexShader, glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH)));
            dispose();
            GameLoop.end();
        }

        // Attach the shader
        glAttachShader(program, vertexShader);
    }

    /**
     * Attach a fragment shader to this program
     * @param name The file name of the vertex shader
     */
    public void attachFragmentShader(String name) {
        // Load source
        String fragmentShaderSource = FileUtil.readFromFile(name);

        // Create shader and set the source
        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);

        glCompileShader(fragmentShader);

        // Check for errors
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Unable to create fragment shader");
            System.err.println(glGetShaderInfoLog(fragmentShader, glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH)));
            dispose();
            GameLoop.end();
        }

        // Attach the shader
        glAttachShader(program, fragmentShader);
    }

    public void setUniforms(String name, float... values) {
        if (values.length > 4) {
            System.err.println("Uniforms cannot have more than 4 values");
            GameLoop.end();
        }

        // Get the location of the uniforms
        int location = glGetUniformLocation(program, name);

        // Set the uniform values
        switch (values.length) {
            case 1:
                glUniform1f(location, values[0]);
                break;
            case 2:
                glUniform2f(location, values[0], values[1]);
                break;
            case 3:
                glUniform3f(location, values[0], values[1], values[2]);
                break;
            case 4:
                glUniform4f(location, values[0], values[1], values[2], values[3]);
        }
    }

    public void setUniform(String name, FloatBuffer matrix) {
        int location = glGetUniformLocation(program, name);
        glUniformMatrix4(location, false, matrix);
    }

    /**
     * Links  this program in order to use
     */
    public void link() {
        glLinkProgram(program);

        // Check for errors
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Unable to link program: ");
            dispose();
            GameLoop.end();
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unBind() {
        glUseProgram(0);
    }

    public void dispose() {
        unBind();

        // Detach the shaders
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);

        // Delete the shaders
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        // Delete the program
        glDeleteProgram(program);
    }
}
