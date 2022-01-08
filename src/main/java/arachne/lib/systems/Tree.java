package arachne.lib.systems;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import static arachne.lib.systems.Tree.OperationFilter.*;

public interface Tree<TreeT extends Tree<TreeT>>
{
	void setParent(TreeT parent);

	TreeT getSelfAsTree();
	TreeT getParent();
	Set<TreeT> getChildren();
	
	public static class OperationFilter {
		public static final int
				ALL = 0,
				THIS = 1,
				ANCESTORS = 1 << 1,
				DESCENDANTS = 1 << 2;
		
		public static final int
				FAMILY_LINE = THIS | ANCESTORS | DESCENDANTS;
	}
	
	default boolean search(int nodesToTraverse, Predicate<TreeT> condition) {
		return search(getSelfAsTree(), nodesToTraverse, condition);
	}
	
	private static <TreeT extends Tree<TreeT>> boolean search(TreeT initialNode, int nodesToTraverse, Predicate<TreeT> condition) {
		if(initialNode == null) return false;
		
		// If there are no filters, search the whole tree
		if(nodesToTraverse == ALL) {
			TreeT parent = initialNode.getParent();
			
			// If current node has a parent, elevate the starting point to the parent
			if(parent != null) return search(parent, ALL, condition);
			
			// If the current node is the top of the tree, do a depth first search
			return search(initialNode, THIS | DESCENDANTS, condition);
		}
		
		// Test each filter
		if((nodesToTraverse & THIS) > 0) {
			if(condition.test(initialNode)) return true;
		}
		
		if((nodesToTraverse & ANCESTORS) > 0) {
			if(search(initialNode.getParent(), THIS | ANCESTORS, condition)) return true;
		}
		
		if((nodesToTraverse & DESCENDANTS) > 0) {
			for(TreeT child : initialNode.getChildren()) {
				if(search(child, THIS | DESCENDANTS, condition)) return true;
			}
		}
		
		return false;
	}
	
	default void apply(int nodesToTraverse, Consumer<TreeT> operation) {
		apply(getSelfAsTree(), nodesToTraverse, operation);
	}
	
	private static <TreeT extends Tree<TreeT>> void apply(TreeT initialNode, int nodesToTraverse, Consumer<TreeT> operation) {
		if(initialNode == null) return;
		
		// If there are no filters, search the whole tree
		if(nodesToTraverse == ALL) {
			TreeT parent = initialNode.getParent();
			
			// If current node has a parent, elevate the starting point to the parent
			if(parent != null) apply(parent, ALL, operation);
			
			// If the current node is the top of the tree, do a depth first search
			else apply(initialNode, THIS | DESCENDANTS, operation);
		}
		
		// Test each filter
		if((nodesToTraverse & THIS) > 0) {
			operation.accept(initialNode);
		}
		
		if((nodesToTraverse & ANCESTORS) > 0) {
			apply(initialNode.getParent(), THIS | ANCESTORS, operation);
		}
		
		if((nodesToTraverse & DESCENDANTS) > 0) {
			for(TreeT child : initialNode.getChildren()) {
				apply(child, THIS | DESCENDANTS, operation);
			}
		}
	}
}
