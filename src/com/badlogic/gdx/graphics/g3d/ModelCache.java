package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;
import java.util.Comparator;

public class ModelCache implements Disposable, RenderableProvider {
  public static class SimpleMeshPool implements MeshPool {
    private Array<Mesh> freeMeshes = new Array<>();
    
    private Array<Mesh> usedMeshes = new Array<>();
    
    public void flush() {
      this.freeMeshes.addAll(this.usedMeshes);
      this.usedMeshes.clear();
    }
    
    public Mesh obtain(VertexAttributes vertexAttributes, int vertexCount, int indexCount) {
      for (int i = 0, n = this.freeMeshes.size; i < n; i++) {
        Mesh mesh = this.freeMeshes.get(i);
        if (mesh.getVertexAttributes().equals(vertexAttributes) && mesh.getMaxVertices() >= vertexCount && 
          mesh.getMaxIndices() >= indexCount) {
          this.freeMeshes.removeIndex(i);
          this.usedMeshes.add(mesh);
          return mesh;
        } 
      } 
      vertexCount = 65536;
      indexCount = Math.max(vertexCount, 1 << 32 - Integer.numberOfLeadingZeros(indexCount - 1));
      Mesh result = new Mesh(false, vertexCount, indexCount, vertexAttributes);
      this.usedMeshes.add(result);
      return result;
    }
    
    public void dispose() {
      for (Array.ArrayIterator<Mesh> arrayIterator2 = this.usedMeshes.iterator(); arrayIterator2.hasNext(); ) {
        Mesh m = arrayIterator2.next();
        m.dispose();
      } 
      this.usedMeshes.clear();
      for (Array.ArrayIterator<Mesh> arrayIterator1 = this.freeMeshes.iterator(); arrayIterator1.hasNext(); ) {
        Mesh m = arrayIterator1.next();
        m.dispose();
      } 
      this.freeMeshes.clear();
    }
  }
  
  public static class TightMeshPool implements MeshPool {
    private Array<Mesh> freeMeshes = new Array<>();
    
    private Array<Mesh> usedMeshes = new Array<>();
    
    public void flush() {
      this.freeMeshes.addAll(this.usedMeshes);
      this.usedMeshes.clear();
    }
    
    public Mesh obtain(VertexAttributes vertexAttributes, int vertexCount, int indexCount) {
      for (int i = 0, n = this.freeMeshes.size; i < n; i++) {
        Mesh mesh = this.freeMeshes.get(i);
        if (mesh.getVertexAttributes().equals(vertexAttributes) && mesh.getMaxVertices() == vertexCount && 
          mesh.getMaxIndices() == indexCount) {
          this.freeMeshes.removeIndex(i);
          this.usedMeshes.add(mesh);
          return mesh;
        } 
      } 
      Mesh result = new Mesh(true, vertexCount, indexCount, vertexAttributes);
      this.usedMeshes.add(result);
      return result;
    }
    
    public void dispose() {
      for (Array.ArrayIterator<Mesh> arrayIterator2 = this.usedMeshes.iterator(); arrayIterator2.hasNext(); ) {
        Mesh m = arrayIterator2.next();
        m.dispose();
      } 
      this.usedMeshes.clear();
      for (Array.ArrayIterator<Mesh> arrayIterator1 = this.freeMeshes.iterator(); arrayIterator1.hasNext(); ) {
        Mesh m = arrayIterator1.next();
        m.dispose();
      } 
      this.freeMeshes.clear();
    }
  }
  
  public static class Sorter implements RenderableSorter, Comparator<Renderable> {
    public void sort(Camera camera, Array<Renderable> renderables) {
      renderables.sort(this);
    }
    
    public int compare(Renderable arg0, Renderable arg1) {
      VertexAttributes va0 = arg0.meshPart.mesh.getVertexAttributes();
      VertexAttributes va1 = arg1.meshPart.mesh.getVertexAttributes();
      int vc = va0.compareTo(va1);
      if (vc == 0) {
        int mc = arg0.material.compareTo(arg1.material);
        if (mc == 0)
          return arg0.meshPart.primitiveType - arg1.meshPart.primitiveType; 
        return mc;
      } 
      return vc;
    }
  }
  
  private Array<Renderable> renderables = new Array<>();
  
  private FlushablePool<Renderable> renderablesPool = new FlushablePool<Renderable>() {
      protected Renderable newObject() {
        return new Renderable();
      }
    };
  
  private FlushablePool<MeshPart> meshPartPool = new FlushablePool<MeshPart>() {
      protected MeshPart newObject() {
        return new MeshPart();
      }
    };
  
  private Array<Renderable> items = new Array<>();
  
  private Array<Renderable> tmp = new Array<>();
  
  private MeshBuilder meshBuilder;
  
  private boolean building;
  
  private RenderableSorter sorter;
  
  private MeshPool meshPool;
  
  private Camera camera;
  
  public ModelCache() {
    this(new Sorter(), new SimpleMeshPool());
  }
  
  public ModelCache(RenderableSorter sorter, MeshPool meshPool) {
    this.sorter = sorter;
    this.meshPool = meshPool;
    this.meshBuilder = new MeshBuilder();
  }
  
  public void begin() {
    begin(null);
  }
  
  public void begin(Camera camera) {
    if (this.building)
      throw new GdxRuntimeException("Call end() after calling begin()"); 
    this.building = true;
    this.camera = camera;
    this.renderablesPool.flush();
    this.renderables.clear();
    this.items.clear();
    this.meshPartPool.flush();
    this.meshPool.flush();
  }
  
  private Renderable obtainRenderable(Material material, int primitiveType) {
    Renderable result = this.renderablesPool.obtain();
    result.bones = null;
    result.environment = null;
    result.material = material;
    result.meshPart.mesh = null;
    result.meshPart.offset = 0;
    result.meshPart.size = 0;
    result.meshPart.primitiveType = primitiveType;
    result.meshPart.center.set(0.0F, 0.0F, 0.0F);
    result.meshPart.halfExtents.set(0.0F, 0.0F, 0.0F);
    result.meshPart.radius = -1.0F;
    result.shader = null;
    result.userData = null;
    result.worldTransform.idt();
    return result;
  }
  
  public void end() {
    if (!this.building)
      throw new GdxRuntimeException("Call begin() prior to calling end()"); 
    this.building = false;
    if (this.items.size == 0)
      return; 
    this.sorter.sort(this.camera, this.items);
    int itemCount = this.items.size;
    int initCount = this.renderables.size;
    Renderable first = this.items.get(0);
    VertexAttributes vertexAttributes = first.meshPart.mesh.getVertexAttributes();
    Material material = first.material;
    int primitiveType = first.meshPart.primitiveType;
    int offset = this.renderables.size;
    this.meshBuilder.begin(vertexAttributes);
    MeshPart part = this.meshBuilder.part("", primitiveType, this.meshPartPool.obtain());
    this.renderables.add(obtainRenderable(material, primitiveType));
    for (int i = 0, n = this.items.size; i < n; i++) {
      Renderable renderable = this.items.get(i);
      VertexAttributes va = renderable.meshPart.mesh.getVertexAttributes();
      Material mat = renderable.material;
      int pt = renderable.meshPart.primitiveType;
      boolean sameAttributes = va.equals(vertexAttributes);
      boolean indexedMesh = (renderable.meshPart.mesh.getNumIndices() > 0);
      int verticesToAdd = indexedMesh ? renderable.meshPart.mesh.getNumVertices() : renderable.meshPart.size;
      boolean canHoldVertices = (this.meshBuilder.getNumVertices() + verticesToAdd <= 65536);
      boolean sameMesh = (sameAttributes && canHoldVertices);
      boolean samePart = (sameMesh && pt == primitiveType && mat.same(material, true));
      if (!samePart) {
        if (!sameMesh) {
          Mesh mesh1 = this.meshBuilder.end(this.meshPool.obtain(vertexAttributes, this.meshBuilder.getNumVertices(), 
                this.meshBuilder.getNumIndices()));
          while (offset < this.renderables.size)
            ((Renderable)this.renderables.get(offset++)).meshPart.mesh = mesh1; 
          this.meshBuilder.begin(vertexAttributes = va);
        } 
        MeshPart newPart = this.meshBuilder.part("", pt, this.meshPartPool.obtain());
        Renderable renderable1 = this.renderables.get(this.renderables.size - 1);
        renderable1.meshPart.offset = part.offset;
        renderable1.meshPart.size = part.size;
        part = newPart;
        this.renderables.add(obtainRenderable(material = mat, primitiveType = pt));
      } 
      this.meshBuilder.setVertexTransform(renderable.worldTransform);
      this.meshBuilder.addMesh(renderable.meshPart.mesh, renderable.meshPart.offset, renderable.meshPart.size);
    } 
    Mesh mesh = this.meshBuilder.end(this.meshPool.obtain(vertexAttributes, this.meshBuilder.getNumVertices(), 
          this.meshBuilder.getNumIndices()));
    while (offset < this.renderables.size)
      ((Renderable)this.renderables.get(offset++)).meshPart.mesh = mesh; 
    Renderable previous = this.renderables.get(this.renderables.size - 1);
    previous.meshPart.offset = part.offset;
    previous.meshPart.size = part.size;
  }
  
  public void add(Renderable renderable) {
    if (!this.building)
      throw new GdxRuntimeException("Can only add items to the ModelCache in between .begin() and .end()"); 
    if (renderable.bones == null) {
      this.items.add(renderable);
    } else
      this.renderables.add(renderable); 
  }
  
  public void add(RenderableProvider renderableProvider) {
    renderableProvider.getRenderables(this.tmp, this.renderablesPool);
    for (int i = 0, n = this.tmp.size; i < n; i++)
      add(this.tmp.get(i)); 
    this.tmp.clear();
  }
  
  public <T extends RenderableProvider> void add(Iterable<T> renderableProviders) {
    for (RenderableProvider renderableProvider : (Iterable<RenderableProvider>)renderableProviders)
      add(renderableProvider); 
  }
  
  public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
    if (this.building)
      throw new GdxRuntimeException("Cannot render a ModelCache in between .begin() and .end()"); 
    for (Array.ArrayIterator<Renderable> arrayIterator = this.renderables.iterator(); arrayIterator.hasNext(); ) {
      Renderable r = arrayIterator.next();
      r.shader = null;
      r.environment = null;
    } 
    renderables.addAll(this.renderables);
  }
  
  public void dispose() {
    if (this.building)
      throw new GdxRuntimeException("Cannot dispose a ModelCache in between .begin() and .end()"); 
    this.meshPool.dispose();
  }
  
  public static interface MeshPool extends Disposable {
    Mesh obtain(VertexAttributes param1VertexAttributes, int param1Int1, int param1Int2);
    
    void flush();
  }
}
