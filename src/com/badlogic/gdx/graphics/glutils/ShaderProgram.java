package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ShaderProgram implements Disposable {
  public static final String POSITION_ATTRIBUTE = "a_position";
  
  public static final String NORMAL_ATTRIBUTE = "a_normal";
  
  public static final String COLOR_ATTRIBUTE = "a_color";
  
  public static final String TEXCOORD_ATTRIBUTE = "a_texCoord";
  
  public static final String TANGENT_ATTRIBUTE = "a_tangent";
  
  public static final String BINORMAL_ATTRIBUTE = "a_binormal";
  
  public static final String BONEWEIGHT_ATTRIBUTE = "a_boneWeight";
  
  public static boolean pedantic = true;
  
  public static String prependVertexCode = "";
  
  public static String prependFragmentCode = "";
  
  private static final ObjectMap<Application, Array<ShaderProgram>> shaders = new ObjectMap<>();
  
  private String log = "";
  
  private boolean isCompiled;
  
  private final ObjectIntMap<String> uniforms = new ObjectIntMap<>();
  
  private final ObjectIntMap<String> uniformTypes = new ObjectIntMap<>();
  
  private final ObjectIntMap<String> uniformSizes = new ObjectIntMap<>();
  
  private String[] uniformNames;
  
  private final ObjectIntMap<String> attributes = new ObjectIntMap<>();
  
  private final ObjectIntMap<String> attributeTypes = new ObjectIntMap<>();
  
  private final ObjectIntMap<String> attributeSizes = new ObjectIntMap<>();
  
  private String[] attributeNames;
  
  private int program;
  
  private int vertexShaderHandle;
  
  private int fragmentShaderHandle;
  
  private final FloatBuffer matrix;
  
  private final String vertexShaderSource;
  
  private final String fragmentShaderSource;
  
  private boolean invalidated;
  
  private int refCount = 0;
  
  public ShaderProgram(String vertexShader, String fragmentShader) {
    if (vertexShader == null)
      throw new IllegalArgumentException("vertex shader must not be null"); 
    if (fragmentShader == null)
      throw new IllegalArgumentException("fragment shader must not be null"); 
    if (prependVertexCode != null && prependVertexCode.length() > 0)
      vertexShader = prependVertexCode + vertexShader; 
    if (prependFragmentCode != null && prependFragmentCode.length() > 0)
      fragmentShader = prependFragmentCode + fragmentShader; 
    this.vertexShaderSource = vertexShader;
    this.fragmentShaderSource = fragmentShader;
    this.matrix = BufferUtils.newFloatBuffer(16);
    compileShaders(vertexShader, fragmentShader);
    if (isCompiled()) {
      fetchAttributes();
      fetchUniforms();
      addManagedShader(Gdx.app, this);
    } 
  }
  
  public ShaderProgram(FileHandle vertexShader, FileHandle fragmentShader) {
    this(vertexShader.readString(), fragmentShader.readString());
  }
  
  private void compileShaders(String vertexShader, String fragmentShader) {
    this.vertexShaderHandle = loadShader(35633, vertexShader);
    this.fragmentShaderHandle = loadShader(35632, fragmentShader);
    if (this.vertexShaderHandle == -1 || this.fragmentShaderHandle == -1) {
      this.isCompiled = false;
      return;
    } 
    this.program = linkProgram(createProgram());
    if (this.program == -1) {
      this.isCompiled = false;
      return;
    } 
    this.isCompiled = true;
  }
  
  private int loadShader(int type, String source) {
    GL20 gl = Gdx.gl20;
    IntBuffer intbuf = BufferUtils.newIntBuffer(1);
    int shader = gl.glCreateShader(type);
    if (shader == 0)
      return -1; 
    gl.glShaderSource(shader, source);
    gl.glCompileShader(shader);
    gl.glGetShaderiv(shader, 35713, intbuf);
    int compiled = intbuf.get(0);
    if (compiled == 0) {
      String infoLog = gl.glGetShaderInfoLog(shader);
      this.log += (type == 35633) ? "Vertex shader\n" : "Fragment shader:\n";
      this.log += infoLog;
      return -1;
    } 
    return shader;
  }
  
  protected int createProgram() {
    GL20 gl = Gdx.gl20;
    int program = gl.glCreateProgram();
    return (program != 0) ? program : -1;
  }
  
  private int linkProgram(int program) {
    GL20 gl = Gdx.gl20;
    if (program == -1)
      return -1; 
    gl.glAttachShader(program, this.vertexShaderHandle);
    gl.glAttachShader(program, this.fragmentShaderHandle);
    gl.glLinkProgram(program);
    ByteBuffer tmp = ByteBuffer.allocateDirect(4);
    tmp.order(ByteOrder.nativeOrder());
    IntBuffer intbuf = tmp.asIntBuffer();
    gl.glGetProgramiv(program, 35714, intbuf);
    int linked = intbuf.get(0);
    if (linked == 0) {
      this.log = Gdx.gl20.glGetProgramInfoLog(program);
      return -1;
    } 
    return program;
  }
  
  static final IntBuffer intbuf = BufferUtils.newIntBuffer(1);
  
  public String getLog() {
    if (this.isCompiled) {
      this.log = Gdx.gl20.glGetProgramInfoLog(this.program);
      return this.log;
    } 
    return this.log;
  }
  
  public boolean isCompiled() {
    return this.isCompiled;
  }
  
  private int fetchAttributeLocation(String name) {
    GL20 gl = Gdx.gl20;
    int location;
    if ((location = this.attributes.get(name, -2)) == -2) {
      location = gl.glGetAttribLocation(this.program, name);
      this.attributes.put(name, location);
    } 
    return location;
  }
  
  private int fetchUniformLocation(String name) {
    return fetchUniformLocation(name, pedantic);
  }
  
  public int fetchUniformLocation(String name, boolean pedantic) {
    int location;
    if ((location = this.uniforms.get(name, -2)) == -2) {
      location = Gdx.gl20.glGetUniformLocation(this.program, name);
      if (location == -1 && pedantic) {
        if (this.isCompiled)
          throw new IllegalArgumentException("No uniform with name '" + name + "' in shader"); 
        throw new IllegalStateException("An attempted fetch uniform from uncompiled shader \n" + getLog());
      } 
      this.uniforms.put(name, location);
    } 
    return location;
  }
  
  public void setUniformi(String name, int value) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform1i(location, value);
  }
  
  public void setUniformi(int location, int value) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform1i(location, value);
  }
  
  public void setUniformi(String name, int value1, int value2) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform2i(location, value1, value2);
  }
  
  public void setUniformi(int location, int value1, int value2) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform2i(location, value1, value2);
  }
  
  public void setUniformi(String name, int value1, int value2, int value3) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform3i(location, value1, value2, value3);
  }
  
  public void setUniformi(int location, int value1, int value2, int value3) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform3i(location, value1, value2, value3);
  }
  
  public void setUniformi(String name, int value1, int value2, int value3, int value4) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform4i(location, value1, value2, value3, value4);
  }
  
  public void setUniformi(int location, int value1, int value2, int value3, int value4) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform4i(location, value1, value2, value3, value4);
  }
  
  public void setUniformf(String name, float value) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform1f(location, value);
  }
  
  public void setUniformf(int location, float value) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform1f(location, value);
  }
  
  public void setUniformf(String name, float value1, float value2) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform2f(location, value1, value2);
  }
  
  public void setUniformf(int location, float value1, float value2) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform2f(location, value1, value2);
  }
  
  public void setUniformf(String name, float value1, float value2, float value3) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform3f(location, value1, value2, value3);
  }
  
  public void setUniformf(int location, float value1, float value2, float value3) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform3f(location, value1, value2, value3);
  }
  
  public void setUniformf(String name, float value1, float value2, float value3, float value4) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform4f(location, value1, value2, value3, value4);
  }
  
  public void setUniformf(int location, float value1, float value2, float value3, float value4) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform4f(location, value1, value2, value3, value4);
  }
  
  public void setUniform1fv(String name, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform1fv(location, length, values, offset);
  }
  
  public void setUniform1fv(int location, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform1fv(location, length, values, offset);
  }
  
  public void setUniform2fv(String name, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform2fv(location, length / 2, values, offset);
  }
  
  public void setUniform2fv(int location, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform2fv(location, length / 2, values, offset);
  }
  
  public void setUniform3fv(String name, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform3fv(location, length / 3, values, offset);
  }
  
  public void setUniform3fv(int location, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform3fv(location, length / 3, values, offset);
  }
  
  public void setUniform4fv(String name, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchUniformLocation(name);
    gl.glUniform4fv(location, length / 4, values, offset);
  }
  
  public void setUniform4fv(int location, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniform4fv(location, length / 4, values, offset);
  }
  
  public void setUniformMatrix(String name, Matrix4 matrix) {
    setUniformMatrix(name, matrix, false);
  }
  
  public void setUniformMatrix(String name, Matrix4 matrix, boolean transpose) {
    setUniformMatrix(fetchUniformLocation(name), matrix, transpose);
  }
  
  public void setUniformMatrix(int location, Matrix4 matrix) {
    setUniformMatrix(location, matrix, false);
  }
  
  public void setUniformMatrix(int location, Matrix4 matrix, boolean transpose) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniformMatrix4fv(location, 1, transpose, matrix.val, 0);
  }
  
  public void setUniformMatrix(String name, Matrix3 matrix) {
    setUniformMatrix(name, matrix, false);
  }
  
  public void setUniformMatrix(String name, Matrix3 matrix, boolean transpose) {
    setUniformMatrix(fetchUniformLocation(name), matrix, transpose);
  }
  
  public void setUniformMatrix(int location, Matrix3 matrix) {
    setUniformMatrix(location, matrix, false);
  }
  
  public void setUniformMatrix(int location, Matrix3 matrix, boolean transpose) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniformMatrix3fv(location, 1, transpose, matrix.val, 0);
  }
  
  public void setUniformMatrix3fv(String name, FloatBuffer buffer, int count, boolean transpose) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    buffer.position(0);
    int location = fetchUniformLocation(name);
    gl.glUniformMatrix3fv(location, count, transpose, buffer);
  }
  
  public void setUniformMatrix4fv(String name, FloatBuffer buffer, int count, boolean transpose) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    buffer.position(0);
    int location = fetchUniformLocation(name);
    gl.glUniformMatrix4fv(location, count, transpose, buffer);
  }
  
  public void setUniformMatrix4fv(int location, float[] values, int offset, int length) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUniformMatrix4fv(location, length / 16, false, values, offset);
  }
  
  public void setUniformMatrix4fv(String name, float[] values, int offset, int length) {
    setUniformMatrix4fv(fetchUniformLocation(name), values, offset, length);
  }
  
  public void setUniformf(String name, Vector2 values) {
    setUniformf(name, values.x, values.y);
  }
  
  public void setUniformf(int location, Vector2 values) {
    setUniformf(location, values.x, values.y);
  }
  
  public void setUniformf(String name, Vector3 values) {
    setUniformf(name, values.x, values.y, values.z);
  }
  
  public void setUniformf(int location, Vector3 values) {
    setUniformf(location, values.x, values.y, values.z);
  }
  
  public void setUniformf(String name, Color values) {
    setUniformf(name, values.r, values.g, values.b, values.a);
  }
  
  public void setUniformf(int location, Color values) {
    setUniformf(location, values.r, values.g, values.b, values.a);
  }
  
  public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, Buffer buffer) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchAttributeLocation(name);
    if (location == -1)
      return; 
    gl.glVertexAttribPointer(location, size, type, normalize, stride, buffer);
  }
  
  public void setVertexAttribute(int location, int size, int type, boolean normalize, int stride, Buffer buffer) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glVertexAttribPointer(location, size, type, normalize, stride, buffer);
  }
  
  public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, int offset) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchAttributeLocation(name);
    if (location == -1)
      return; 
    gl.glVertexAttribPointer(location, size, type, normalize, stride, offset);
  }
  
  public void setVertexAttribute(int location, int size, int type, boolean normalize, int stride, int offset) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glVertexAttribPointer(location, size, type, normalize, stride, offset);
  }
  
  @Deprecated
  public void begin() {
    bind();
  }
  
  public void bind() {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glUseProgram(this.program);
  }
  
  @Deprecated
  public void end() {}
  
  public void dispose() {
    GL20 gl = Gdx.gl20;
    gl.glUseProgram(0);
    gl.glDeleteShader(this.vertexShaderHandle);
    gl.glDeleteShader(this.fragmentShaderHandle);
    gl.glDeleteProgram(this.program);
    if (shaders.<Application>get(Gdx.app) != null)
      shaders.<Application>get(Gdx.app).removeValue(this, true); 
  }
  
  public void disableVertexAttribute(String name) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchAttributeLocation(name);
    if (location == -1)
      return; 
    gl.glDisableVertexAttribArray(location);
  }
  
  public void disableVertexAttribute(int location) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glDisableVertexAttribArray(location);
  }
  
  public void enableVertexAttribute(String name) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    int location = fetchAttributeLocation(name);
    if (location == -1)
      return; 
    gl.glEnableVertexAttribArray(location);
  }
  
  public void enableVertexAttribute(int location) {
    GL20 gl = Gdx.gl20;
    checkManaged();
    gl.glEnableVertexAttribArray(location);
  }
  
  private void checkManaged() {
    if (this.invalidated) {
      compileShaders(this.vertexShaderSource, this.fragmentShaderSource);
      this.invalidated = false;
    } 
  }
  
  private void addManagedShader(Application app, ShaderProgram shaderProgram) {
    Array<ShaderProgram> managedResources = shaders.<Application>get(app);
    if (managedResources == null)
      managedResources = new Array<>(); 
    managedResources.add(shaderProgram);
    shaders.put(app, managedResources);
  }
  
  public static void invalidateAllShaderPrograms(Application app) {
    if (Gdx.gl20 == null)
      return; 
    Array<ShaderProgram> shaderArray = shaders.<Application>get(app);
    if (shaderArray == null)
      return; 
    for (int i = 0; i < shaderArray.size; i++) {
      ((ShaderProgram)shaderArray.get(i)).invalidated = true;
      shaderArray.get(i).checkManaged();
    } 
  }
  
  public static void clearAllShaderPrograms(Application app) {
    shaders.remove(app);
  }
  
  public static String getManagedStatus() {
    StringBuilder builder = new StringBuilder();
    int i = 0;
    builder.append("Managed shaders/app: { ");
    for (ObjectMap.Keys<Application> keys = shaders.keys().iterator(); keys.hasNext(); ) {
      Application app = keys.next();
      builder.append(((Array)shaders.get(app)).size);
      builder.append(" ");
    } 
    builder.append("}");
    return builder.toString();
  }
  
  public static int getNumManagedShaderPrograms() {
    return ((Array)shaders.get(Gdx.app)).size;
  }
  
  public void setAttributef(String name, float value1, float value2, float value3, float value4) {
    GL20 gl = Gdx.gl20;
    int location = fetchAttributeLocation(name);
    gl.glVertexAttrib4f(location, value1, value2, value3, value4);
  }
  
  IntBuffer params = BufferUtils.newIntBuffer(1);
  
  IntBuffer type = BufferUtils.newIntBuffer(1);
  
  private void fetchUniforms() {
    this.params.clear();
    Gdx.gl20.glGetProgramiv(this.program, 35718, this.params);
    int numUniforms = this.params.get(0);
    this.uniformNames = new String[numUniforms];
    for (int i = 0; i < numUniforms; i++) {
      this.params.clear();
      this.params.put(0, 1);
      this.type.clear();
      String name = Gdx.gl20.glGetActiveUniform(this.program, i, this.params, this.type);
      int location = Gdx.gl20.glGetUniformLocation(this.program, name);
      this.uniforms.put(name, location);
      this.uniformTypes.put(name, this.type.get(0));
      this.uniformSizes.put(name, this.params.get(0));
      this.uniformNames[i] = name;
    } 
  }
  
  private void fetchAttributes() {
    this.params.clear();
    Gdx.gl20.glGetProgramiv(this.program, 35721, this.params);
    int numAttributes = this.params.get(0);
    this.attributeNames = new String[numAttributes];
    for (int i = 0; i < numAttributes; i++) {
      this.params.clear();
      this.params.put(0, 1);
      this.type.clear();
      String name = Gdx.gl20.glGetActiveAttrib(this.program, i, this.params, this.type);
      int location = Gdx.gl20.glGetAttribLocation(this.program, name);
      this.attributes.put(name, location);
      this.attributeTypes.put(name, this.type.get(0));
      this.attributeSizes.put(name, this.params.get(0));
      this.attributeNames[i] = name;
    } 
  }
  
  public boolean hasAttribute(String name) {
    return this.attributes.containsKey(name);
  }
  
  public int getAttributeType(String name) {
    return this.attributeTypes.get(name, 0);
  }
  
  public int getAttributeLocation(String name) {
    return this.attributes.get(name, -1);
  }
  
  public int getAttributeSize(String name) {
    return this.attributeSizes.get(name, 0);
  }
  
  public boolean hasUniform(String name) {
    return this.uniforms.containsKey(name);
  }
  
  public int getUniformType(String name) {
    return this.uniformTypes.get(name, 0);
  }
  
  public int getUniformLocation(String name) {
    return this.uniforms.get(name, -1);
  }
  
  public int getUniformSize(String name) {
    return this.uniformSizes.get(name, 0);
  }
  
  public String[] getAttributes() {
    return this.attributeNames;
  }
  
  public String[] getUniforms() {
    return this.uniformNames;
  }
  
  public String getVertexShaderSource() {
    return this.vertexShaderSource;
  }
  
  public String getFragmentShaderSource() {
    return this.fragmentShaderSource;
  }
  
  public int getHandle() {
    return this.program;
  }
}
