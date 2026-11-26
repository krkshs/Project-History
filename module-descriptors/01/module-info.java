module org.lwjgl.glfw {
  requires java.base;
  requires transitive org.lwjgl;
  requires static org.lwjgl.egl;
  requires static org.lwjgl.opengl;
  requires static org.lwjgl.vulkan;
  
  exports org.lwjgl.glfw;
}
