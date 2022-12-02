package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class  Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key) {
        this.key = key;
        this.children = new ArrayList<>();
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();
        traverseTreeWithRecursion(builder, 0, this);

        return builder.toString().trim();
    }

    private void traverseTreeWithRecursion(StringBuilder builder, int indent, Tree<E> tree) {
        builder
                .append(this.getPadding(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecursion(builder, indent + 2, child);
        }

    }

    private void traverseTreeWithRecursion(List<Tree<E>> collection, Tree<E> tree) {
        collection.add(tree);

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecursion(collection, child);
        }

    }

    private List<Tree<E>> traverseWithBFS() {
        StringBuilder builder = new StringBuilder();

        Deque<Tree<E>> queue = new ArrayDeque<>();

        queue.offer(this);

        List<Tree<E>> allNodes = new ArrayList<>();

        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            allNodes.add(tree);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }
        return allNodes;
    }

    private String getPadding(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        return traverseWithBFS()
                .stream()
                .filter(tree -> tree.children.size() == 0 )
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNodes = new ArrayList<>();
        this.traverseTreeWithRecursion(allNodes, this);
        return allNodes.stream()
                .filter(tree -> tree.parent != null && tree.children.size() > 0)
                .map(Tree::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> trees = this.traverseWithBFS();
        int maxPath = 0;
        Tree<E> deepestLeftMostNode = null;
        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLeftMostNode = tree;
                }
            }
        }

        return deepestLeftMostNode;
    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;
        while (current.parent != null) {

            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.isEmpty();
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



