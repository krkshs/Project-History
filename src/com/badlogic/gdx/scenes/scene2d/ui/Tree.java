package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

public class Tree<N extends Tree.Node, V> extends WidgetGroup {
  private static final Vector2 tmp = new Vector2();
  
  TreeStyle style;
  
  final Array<N> rootNodes = new Array<>();
  
  final Selection<N> selection;
  
  float ySpacing = 4.0F;
  
  float iconSpacingLeft = 2.0F;
  
  float iconSpacingRight = 2.0F;
  
  float paddingLeft;
  
  float paddingRight;
  
  float indentSpacing;
  
  private float prefWidth;
  
  private float prefHeight;
  
  private boolean sizeInvalid = true;
  
  private N foundNode;
  
  private N overNode;
  
  N rangeStart;
  
  private ClickListener clickListener;
  
  public Tree(Skin skin) {
    this(skin.<TreeStyle>get(TreeStyle.class));
  }
  
  public Tree(Skin skin, String styleName) {
    this(skin.<TreeStyle>get(styleName, TreeStyle.class));
  }
  
  public Tree(TreeStyle style) {
    this.selection = new Selection<N>() {
        protected void changed() {
          switch (size()) {
            case 0:
              Tree.this.rangeStart = null;
              break;
            case 1:
              Tree.this.rangeStart = first();
              break;
          } 
        }
      };
    this.selection.setActor(this);
    this.selection.setMultiple(true);
    setStyle(style);
    initialize();
  }
  
  private void initialize() {
    addListener(this.clickListener = new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            N node = Tree.this.getNodeAt(y);
            if (node == null)
              return; 
            if (node != Tree.this.getNodeAt(getTouchDownY()))
              return; 
            if (Tree.this.selection.getMultiple() && Tree.this.selection.notEmpty() && UIUtils.shift()) {
              if (Tree.this.rangeStart == null)
                Tree.this.rangeStart = node; 
              N rangeStart = Tree.this.rangeStart;
              if (!UIUtils.ctrl())
                Tree.this.selection.clear(); 
              float start = rangeStart.actor.getY(), end = node.actor.getY();
              if (start > end) {
                Tree.this.selectNodes(Tree.this.rootNodes, end, start);
              } else {
                Tree.this.selectNodes(Tree.this.rootNodes, start, end);
                Tree.this.selection.items().orderedItems().reverse();
              } 
              Tree.this.selection.fireChangeEvent();
              Tree.this.rangeStart = rangeStart;
              return;
            } 
            if (node.children.size > 0 && (!Tree.this.selection.getMultiple() || !UIUtils.ctrl())) {
              float rowX = node.actor.getX();
              if (node.icon != null)
                rowX -= Tree.this.iconSpacingRight + node.icon.getMinWidth(); 
              if (x < rowX) {
                node.setExpanded(!node.expanded);
                return;
              } 
            } 
            if (!node.isSelectable())
              return; 
            Tree.this.selection.choose(node);
            if (!Tree.this.selection.isEmpty())
              Tree.this.rangeStart = node; 
          }
          
          public boolean mouseMoved(InputEvent event, float x, float y) {
            Tree.this.setOverNode(Tree.this.getNodeAt(y));
            return false;
          }
          
          public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            super.enter(event, x, y, pointer, fromActor);
            Tree.this.setOverNode(Tree.this.getNodeAt(y));
          }
          
          public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
            super.exit(event, x, y, pointer, toActor);
            if (toActor == null || !toActor.isDescendantOf(Tree.this))
              Tree.this.setOverNode(null); 
          }
        });
  }
  
  public void setStyle(TreeStyle style) {
    this.style = style;
    if (this.indentSpacing == 0.0F)
      this.indentSpacing = plusMinusWidth(); 
  }
  
  public void add(N node) {
    insert(this.rootNodes.size, node);
  }
  
  public void insert(int index, N node) {
    int actorIndex;
    if (node.parent != null) {
      node.parent.remove(node);
      node.parent = null;
    } else {
      int existingIndex = this.rootNodes.indexOf(node, true);
      if (existingIndex != -1) {
        if (existingIndex == index)
          return; 
        if (existingIndex < index)
          index--; 
        this.rootNodes.removeIndex(existingIndex);
        int i = node.actor.getZIndex();
        if (i != -1)
          node.removeFromTree(this, i); 
      } 
    } 
    this.rootNodes.insert(index, node);
    if (index == 0) {
      actorIndex = 0;
    } else if (index < this.rootNodes.size - 1) {
      actorIndex = ((Node)this.rootNodes.get(index + 1)).actor.getZIndex();
    } else {
      N before = this.rootNodes.get(index - 1);
      actorIndex = before.actor.getZIndex() + before.countActors();
    } 
    node.addToTree(this, actorIndex);
  }
  
  public void remove(N node) {
    if (node.parent != null) {
      node.parent.remove(node);
      return;
    } 
    if (!this.rootNodes.removeValue(node, true))
      return; 
    int actorIndex = node.actor.getZIndex();
    if (actorIndex != -1)
      node.removeFromTree(this, actorIndex); 
  }
  
  public void clearChildren(boolean unfocus) {
    super.clearChildren(unfocus);
    setOverNode(null);
    this.rootNodes.clear();
    this.selection.clear();
  }
  
  public void invalidate() {
    super.invalidate();
    this.sizeInvalid = true;
  }
  
  private float plusMinusWidth() {
    float width = Math.max(this.style.plus.getMinWidth(), this.style.minus.getMinWidth());
    if (this.style.plusOver != null)
      width = Math.max(width, this.style.plusOver.getMinWidth()); 
    if (this.style.minusOver != null)
      width = Math.max(width, this.style.minusOver.getMinWidth()); 
    return width;
  }
  
  private void computeSize() {
    this.sizeInvalid = false;
    this.prefWidth = plusMinusWidth();
    this.prefHeight = 0.0F;
    computeSize(this.rootNodes, 0.0F, this.prefWidth);
    this.prefWidth += this.paddingLeft + this.paddingRight;
  }
  
  private void computeSize(Array<N> nodes, float indent, float plusMinusWidth) {
    float ySpacing = this.ySpacing;
    float spacing = this.iconSpacingLeft + this.iconSpacingRight;
    for (int i = 0, n = nodes.size; i < n; i++) {
      N node = nodes.get(i);
      float rowWidth = indent + plusMinusWidth;
      A a = node.actor;
      if (a instanceof Layout) {
        Layout layout = (Layout)a;
        rowWidth += layout.getPrefWidth();
        node.height = layout.getPrefHeight();
      } else {
        rowWidth += a.getWidth();
        node.height = a.getHeight();
      } 
      if (node.icon != null) {
        rowWidth += spacing + node.icon.getMinWidth();
        node.height = Math.max(node.height, node.icon.getMinHeight());
      } 
      this.prefWidth = Math.max(this.prefWidth, rowWidth);
      this.prefHeight += node.height + ySpacing;
      if (node.expanded)
        computeSize(node.children, indent + this.indentSpacing, plusMinusWidth); 
    } 
  }
  
  public void layout() {
    if (this.sizeInvalid)
      computeSize(); 
    layout(this.rootNodes, this.paddingLeft, getHeight() - this.ySpacing / 2.0F, plusMinusWidth());
  }
  
  private float layout(Array<N> nodes, float indent, float y, float plusMinusWidth) {
    float ySpacing = this.ySpacing;
    float iconSpacingLeft = this.iconSpacingLeft;
    float spacing = iconSpacingLeft + this.iconSpacingRight;
    for (int i = 0, n = nodes.size; i < n; i++) {
      N node = nodes.get(i);
      float x = indent + plusMinusWidth;
      if (node.icon != null) {
        x += spacing + node.icon.getMinWidth();
      } else
        x += iconSpacingLeft; 
      if (node.actor instanceof Layout)
        ((Layout)node.actor).pack(); 
      y -= node.getHeight();
      node.actor.setPosition(x, y);
      y -= ySpacing;
      if (node.expanded)
        y = layout(node.children, indent + this.indentSpacing, y, plusMinusWidth); 
    } 
    return y;
  }
  
  public void draw(Batch batch, float parentAlpha) {
    drawBackground(batch, parentAlpha);
    Color color = getColor();
    float a = color.a * parentAlpha;
    batch.setColor(color.r, color.g, color.b, a);
    draw(batch, color.r, color.g, color.b, a, this.rootNodes, this.paddingLeft, plusMinusWidth());
    super.draw(batch, parentAlpha);
  }
  
  protected void drawBackground(Batch batch, float parentAlpha) {
    if (this.style.background != null) {
      Color color = getColor();
      batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
      this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
    } 
  }
  
  private void draw(Batch batch, float r, float g, float b, float a, Array<N> nodes, float indent, float plusMinusWidth) {
    Rectangle cullingArea = getCullingArea();
    float cullBottom = 0.0F, cullTop = 0.0F;
    if (cullingArea != null) {
      cullBottom = cullingArea.y;
      cullTop = cullBottom + cullingArea.height;
    } 
    TreeStyle style = this.style;
    float x = getX(), y = getY(), expandX = x + indent, iconX = expandX + plusMinusWidth + this.iconSpacingLeft;
    for (int i = 0, n = nodes.size; i < n; i++) {
      N node = nodes.get(i);
      A a1 = node.actor;
      float actorY = a1.getY(), height = node.height;
      if (cullingArea == null || (actorY + height >= cullBottom && actorY <= cullTop)) {
        if (this.selection.contains(node) && style.selection != null) {
          drawSelection(node, style.selection, batch, x, y + actorY - this.ySpacing / 2.0F, getWidth(), height + this.ySpacing);
        } else if (node == this.overNode && style.over != null) {
          drawOver(node, style.over, batch, x, y + actorY - this.ySpacing / 2.0F, getWidth(), height + this.ySpacing);
        } 
        if (node.icon != null) {
          float iconY = y + actorY + (float)Math.round((height - node.icon.getMinHeight()) / 2.0F);
          Color actorColor = a1.getColor();
          batch.setColor(actorColor.r, actorColor.g, actorColor.b, actorColor.a * a);
          drawIcon(node, node.icon, batch, iconX, iconY);
          batch.setColor(r, g, b, a);
        } 
        if (node.children.size > 0) {
          Drawable expandIcon = getExpandIcon(node, iconX);
          float iconY = y + actorY + (float)Math.round((height - expandIcon.getMinHeight()) / 2.0F);
          drawExpandIcon(node, expandIcon, batch, expandX, iconY);
        } 
      } else if (actorY < cullBottom) {
        return;
      } 
      if (node.expanded && node.children.size > 0)
        draw(batch, r, g, b, a, node.children, indent + this.indentSpacing, plusMinusWidth); 
    } 
  }
  
  protected void drawSelection(N node, Drawable selection, Batch batch, float x, float y, float width, float height) {
    selection.draw(batch, x, y, width, height);
  }
  
  protected void drawOver(N node, Drawable over, Batch batch, float x, float y, float width, float height) {
    over.draw(batch, x, y, width, height);
  }
  
  protected void drawExpandIcon(N node, Drawable expandIcon, Batch batch, float x, float y) {
    expandIcon.draw(batch, x, y, expandIcon.getMinWidth(), expandIcon.getMinHeight());
  }
  
  protected void drawIcon(N node, Drawable icon, Batch batch, float x, float y) {
    icon.draw(batch, x, y, icon.getMinWidth(), icon.getMinHeight());
  }
  
  protected Drawable getExpandIcon(N node, float iconX) {
    boolean over = false;
    if (node == this.overNode && 
      Gdx.app.getType() == Application.ApplicationType.Desktop && (
      !this.selection.getMultiple() || (!UIUtils.ctrl() && !UIUtils.shift()))) {
      float mouseX = (screenToLocalCoordinates(tmp.set((float)Gdx.input.getX(), 0.0F))).x;
      if (mouseX >= 0.0F && mouseX < iconX)
        over = true; 
    } 
    if (over) {
      Drawable icon = node.expanded ? this.style.minusOver : this.style.plusOver;
      if (icon != null)
        return icon; 
    } 
    return node.expanded ? this.style.minus : this.style.plus;
  }
  
  @Null
  public N getNodeAt(float y) {
    this.foundNode = null;
    getNodeAt(this.rootNodes, y, getHeight());
    return this.foundNode;
  }
  
  private float getNodeAt(Array<N> nodes, float y, float rowY) {
    for (int i = 0, n = nodes.size; i < n; i++) {
      N node = nodes.get(i);
      float height = node.height;
      rowY -= node.getHeight() - height;
      if (y >= rowY - height - this.ySpacing && y < rowY) {
        this.foundNode = node;
        return -1.0F;
      } 
      rowY -= height + this.ySpacing;
      if (node.expanded) {
        rowY = getNodeAt(node.children, y, rowY);
        if (rowY == -1.0F)
          return -1.0F; 
      } 
    } 
    return rowY;
  }
  
  void selectNodes(Array<N> nodes, float low, float high) {
    for (int i = 0, n = nodes.size; i < n; i++) {
      N node = nodes.get(i);
      if (node.actor.getY() < low)
        break; 
      if (node.isSelectable()) {
        if (node.actor.getY() <= high)
          this.selection.add(node); 
        if (node.expanded)
          selectNodes(node.children, low, high); 
      } 
    } 
  }
  
  public Selection<N> getSelection() {
    return this.selection;
  }
  
  @Null
  public N getSelectedNode() {
    return this.selection.first();
  }
  
  @Null
  public V getSelectedValue() {
    N node = this.selection.first();
    return (node == null) ? null : (V)node.getValue();
  }
  
  public TreeStyle getStyle() {
    return this.style;
  }
  
  public Array<N> getRootNodes() {
    return this.rootNodes;
  }
  
  @Deprecated
  public Array<N> getNodes() {
    return this.rootNodes;
  }
  
  public void updateRootNodes() {
    for (int j = 0, k = this.rootNodes.size; j < k; j++) {
      N node = this.rootNodes.get(j);
      int m = node.actor.getZIndex();
      if (m != -1)
        node.removeFromTree(this, m); 
    } 
    for (int i = 0, n = this.rootNodes.size, actorIndex = 0; i < n; i++)
      actorIndex += ((Node)this.rootNodes.get(i)).addToTree(this, actorIndex); 
  }
  
  @Null
  public N getOverNode() {
    return this.overNode;
  }
  
  @Null
  public V getOverValue() {
    if (this.overNode == null)
      return null; 
    return (V)this.overNode.getValue();
  }
  
  public void setOverNode(@Null N overNode) {
    this.overNode = overNode;
  }
  
  public void setPadding(float padding) {
    this.paddingLeft = padding;
    this.paddingRight = padding;
  }
  
  public void setPadding(float left, float right) {
    this.paddingLeft = left;
    this.paddingRight = right;
  }
  
  public void setIndentSpacing(float indentSpacing) {
    this.indentSpacing = indentSpacing;
  }
  
  public float getIndentSpacing() {
    return this.indentSpacing;
  }
  
  public void setYSpacing(float ySpacing) {
    this.ySpacing = ySpacing;
  }
  
  public float getYSpacing() {
    return this.ySpacing;
  }
  
  public void setIconSpacing(float left, float right) {
    this.iconSpacingLeft = left;
    this.iconSpacingRight = right;
  }
  
  public float getPrefWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefWidth;
  }
  
  public float getPrefHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefHeight;
  }
  
  public void findExpandedValues(Array<V> values) {
    findExpandedValues(this.rootNodes, values);
  }
  
  public void restoreExpandedValues(Array<V> values) {
    for (int i = 0, n = values.size; i < n; i++) {
      N node = findNode(values.get(i));
      if (node != null) {
        node.setExpanded(true);
        node.expandTo();
      } 
    } 
  }
  
  static boolean findExpandedValues(Array<? extends Node> nodes, Array values) {
    boolean expanded = false;
    for (int i = 0, n = nodes.size; i < n; i++) {
      Node node = nodes.get(i);
      if (node.expanded && !findExpandedValues((Array)node.children, values))
        values.add(node.value); 
    } 
    return expanded;
  }
  
  @Null
  public N findNode(V value) {
    if (value == null)
      throw new IllegalArgumentException("value cannot be null."); 
    return (N)findNode(this.rootNodes, value);
  }
  
  @Null
  static Node findNode(Array<? extends Node> nodes, Object value) {
    for (int j = 0, k = nodes.size; j < k; j++) {
      Node node = nodes.get(j);
      if (value.equals(node.value))
        return node; 
    } 
    for (int i = 0, n = nodes.size; i < n; i++) {
      Node node = nodes.get(i);
      Node found = findNode((Array)node.children, value);
      if (found != null)
        return found; 
    } 
    return null;
  }
  
  public void collapseAll() {
    collapseAll(this.rootNodes);
  }
  
  static void collapseAll(Array<? extends Node> nodes) {
    for (int i = 0, n = nodes.size; i < n; i++) {
      Node node = nodes.get(i);
      node.setExpanded(false);
      collapseAll((Array)node.children);
    } 
  }
  
  public void expandAll() {
    expandAll(this.rootNodes);
  }
  
  static void expandAll(Array<? extends Node> nodes) {
    for (int i = 0, n = nodes.size; i < n; i++)
      nodes.get(i).expandAll(); 
  }
  
  public ClickListener getClickListener() {
    return this.clickListener;
  }
  
  public static abstract class Node<N extends Node, V, A extends Actor> {
    A actor;
    
    N parent;
    
    final Array<N> children = new Array<>(0);
    
    boolean selectable = true;
    
    boolean expanded;
    
    Drawable icon;
    
    float height;
    
    V value;
    
    public Node(A actor) {
      if (actor == null)
        throw new IllegalArgumentException("actor cannot be null."); 
      this.actor = actor;
    }
    
    public Node() {}
    
    public void setExpanded(boolean expanded) {
      if (expanded == this.expanded)
        return; 
      this.expanded = expanded;
      if (this.children.size == 0)
        return; 
      Tree<N, V> tree = getTree();
      if (tree == null)
        return; 
      Object[] children = this.children.items;
      int actorIndex = this.actor.getZIndex() + 1;
      if (expanded) {
        for (int i = 0, n = this.children.size; i < n; i++)
          actorIndex += ((Node)children[i]).addToTree(tree, actorIndex); 
      } else
        for (int i = 0, n = this.children.size; i < n; i++)
          ((Node)children[i]).removeFromTree(tree, actorIndex);  
    }
    
    protected int addToTree(Tree<N, V> tree, int actorIndex) {
      tree.addActorAt(actorIndex, this.actor);
      if (!this.expanded)
        return 1; 
      int childIndex = actorIndex + 1;
      Object[] children = this.children.items;
      for (int i = 0, n = this.children.size; i < n; i++)
        childIndex += ((Node)children[i]).addToTree(tree, childIndex); 
      return childIndex - actorIndex;
    }
    
    protected void removeFromTree(Tree<N, V> tree, int actorIndex) {
      Actor removeActorAt = tree.removeActorAt(actorIndex, true);
      if (!this.expanded)
        return; 
      Object[] children = this.children.items;
      for (int i = 0, n = this.children.size; i < n; i++)
        ((Node)children[i]).removeFromTree(tree, actorIndex); 
    }
    
    public void add(N node) {
      insert(this.children.size, node);
    }
    
    public void addAll(Array<N> nodes) {
      for (int i = 0, n = nodes.size; i < n; i++)
        insert(this.children.size, nodes.get(i)); 
    }
    
    public void insert(int childIndex, N node) {
      node.parent = (N)this;
      this.children.insert(childIndex, node);
      if (!this.expanded)
        return; 
      Tree<N, V> tree = getTree();
      if (tree != null) {
        int actorIndex;
        if (childIndex == 0) {
          actorIndex = this.actor.getZIndex() + 1;
        } else if (childIndex < this.children.size - 1) {
          actorIndex = ((Node)this.children.get(childIndex + 1)).actor.getZIndex();
        } else {
          N before = this.children.get(childIndex - 1);
          actorIndex = before.actor.getZIndex() + before.countActors();
        } 
        node.addToTree(tree, actorIndex);
      } 
    }
    
    int countActors() {
      if (!this.expanded)
        return 1; 
      int count = 1;
      Object[] children = this.children.items;
      for (int i = 0, n = this.children.size; i < n; i++)
        count += ((Node)children[i]).countActors(); 
      return count;
    }
    
    public void remove() {
      Tree<N, V> tree = getTree();
      if (tree != null) {
        tree.remove((N)this);
      } else if (this.parent != null) {
        this.parent.remove((N)this);
      } 
    }
    
    public void remove(N node) {
      if (!this.children.removeValue(node, true))
        return; 
      if (!this.expanded)
        return; 
      Tree<N, V> tree = getTree();
      if (tree != null)
        node.removeFromTree(tree, node.actor.getZIndex()); 
    }
    
    public void clearChildren() {
      if (this.expanded) {
        Tree<N, V> tree = getTree();
        if (tree != null) {
          int actorIndex = this.actor.getZIndex() + 1;
          Object[] children = this.children.items;
          for (int i = 0, n = this.children.size; i < n; i++)
            ((Node)children[i]).removeFromTree(tree, actorIndex); 
        } 
      } 
      this.children.clear();
    }
    
    @Null
    public Tree<N, V> getTree() {
      Group parent = this.actor.getParent();
      if (parent instanceof Tree)
        return (Tree<N, V>)parent; 
      return null;
    }
    
    public void setActor(A newActor) {
      if (this.actor != null) {
        Tree<N, V> tree = getTree();
        if (tree != null) {
          int index = this.actor.getZIndex();
          tree.removeActorAt(index, true);
          tree.addActorAt(index, newActor);
        } 
      } 
      this.actor = newActor;
    }
    
    public A getActor() {
      return this.actor;
    }
    
    public boolean isExpanded() {
      return this.expanded;
    }
    
    public Array<N> getChildren() {
      return this.children;
    }
    
    public boolean hasChildren() {
      return (this.children.size > 0);
    }
    
    public void updateChildren() {
      if (!this.expanded)
        return; 
      Tree<N, V> tree = getTree();
      if (tree == null)
        return; 
      Object[] children = this.children.items;
      int n = this.children.size;
      int actorIndex = this.actor.getZIndex() + 1;
      for (int j = 0; j < n; j++)
        ((Node)children[j]).removeFromTree(tree, actorIndex); 
      for (int i = 0; i < n; i++)
        actorIndex += ((Node)children[i]).addToTree(tree, actorIndex); 
    }
    
    @Null
    public N getParent() {
      return this.parent;
    }
    
    public void setIcon(@Null Drawable icon) {
      this.icon = icon;
    }
    
    @Null
    public V getValue() {
      return this.value;
    }
    
    public void setValue(@Null V value) {
      this.value = value;
    }
    
    @Null
    public Drawable getIcon() {
      return this.icon;
    }
    
    public int getLevel() {
      int level = 0;
      Node<N, V, A> current = this;
      while (true) {
        level++;
        current = current.getParent();
        if (current == null)
          return level; 
      } 
    }
    
    @Null
    public N findNode(V value) {
      if (value == null)
        throw new IllegalArgumentException("value cannot be null."); 
      if (value.equals(this.value))
        return (N)this; 
      return (N)Tree.findNode(this.children, value);
    }
    
    public void collapseAll() {
      setExpanded(false);
      Tree.collapseAll(this.children);
    }
    
    public void expandAll() {
      setExpanded(true);
      if (this.children.size > 0)
        Tree.expandAll(this.children); 
    }
    
    public void expandTo() {
      N n = this.parent;
      while (n != null) {
        n.setExpanded(true);
        n = n.parent;
      } 
    }
    
    public boolean isSelectable() {
      return this.selectable;
    }
    
    public void setSelectable(boolean selectable) {
      this.selectable = selectable;
    }
    
    public void findExpandedValues(Array<V> values) {
      if (this.expanded && !Tree.findExpandedValues(this.children, values))
        values.add(this.value); 
    }
    
    public void restoreExpandedValues(Array<V> values) {
      for (int i = 0, n = values.size; i < n; i++) {
        N node = findNode(values.get(i));
        if (node != null) {
          node.setExpanded(true);
          node.expandTo();
        } 
      } 
    }
    
    public float getHeight() {
      return this.height;
    }
    
    public boolean isAscendantOf(N node) {
      if (node == null)
        throw new IllegalArgumentException("node cannot be null."); 
      N n = node;
      while (true) {
        if (n == this)
          return true; 
        n = n.parent;
        if (n == null)
          return false; 
      } 
    }
    
    public boolean isDescendantOf(N node) {
      if (node == null)
        throw new IllegalArgumentException("node cannot be null."); 
      Node<N, V, A> parent = this;
      while (true) {
        if (parent == node)
          return true; 
        N n = parent.parent;
        if (n == null)
          return false; 
      } 
    }
  }
  
  public static class TreeStyle {
    public Drawable plus;
    
    public Drawable minus;
    
    @Null
    public Drawable plusOver;
    
    @Null
    public Drawable minusOver;
    
    @Null
    public Drawable over;
    
    @Null
    public Drawable selection;
    
    @Null
    public Drawable background;
    
    public TreeStyle() {}
    
    public TreeStyle(Drawable plus, Drawable minus, @Null Drawable selection) {
      this.plus = plus;
      this.minus = minus;
      this.selection = selection;
    }
    
    public TreeStyle(TreeStyle style) {
      this.plus = style.plus;
      this.minus = style.minus;
      this.plusOver = style.plusOver;
      this.minusOver = style.minusOver;
      this.over = style.over;
      this.selection = style.selection;
      this.background = style.background;
    }
  }
}
