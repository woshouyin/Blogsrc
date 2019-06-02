package database.gas;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Stack;

/***
 * 	基于堆栈和队列的树，用于保存评论的跟随关系，可通过静态方法
 * 	genCmtTreeGas(Stack<CmtGas> cmts)生成。
 */

public class CmtTreeGas {
	private CmtGas cmt;
	private ArrayDeque<CmtTreeGas> children = new ArrayDeque<>();
	
	public CmtTreeGas(CmtGas cmt) {
		this.cmt = cmt;
	}
	
	public void add(CmtTreeGas cg) {
		children.addLast(cg);
	}
	
	public CmtTreeGas next() {
		return children.removeFirst();
	}
	
	public CmtGas getCmt() {
		return cmt;
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	/**
	 * 生成评论树
	 * @param cmts
	 * 入栈顺序即为子树的出队顺序， 若子节点在父节点之后入栈，
	 * 该子节点将不会在生成的树中出现。若按时间排序，先发布的评论应当后入栈。
	 * @return
	 * 返回根节点
	 */
	public static CmtTreeGas genCmtTree(Stack<CmtGas> cmts) {
		HashMap<Long, CmtTreeGas> nodes = new HashMap<>();
		CmtTreeGas root = new CmtTreeGas(null);
		nodes.put((long) -1, root);
		while(!cmts.isEmpty()) {
			CmtGas cmt = cmts.pop();
			CmtTreeGas parent  = nodes.get(cmt.getDirectId());
			if(parent != null) {
				CmtTreeGas t = new CmtTreeGas(cmt);
				parent.add(t);
				nodes.put(cmt.getId(), t);
			}
		}
		return root;
	}
	
}
