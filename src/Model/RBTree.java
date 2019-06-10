/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package src.Model;

/**
 *
 * @author Riquito
 */
public class RBTree extends Tree {
	public static final short BLACK = 0;
	public static final short RED = 1;

	RBNode root;

	/** Creates a new instance of RBTree */
	public RBTree() {
		super();
	}

	private void leftRotate(RBNode Main_node) {
		RBNode Child_node = Main_node.right;
		Main_node.right = Child_node.left;

		if (Child_node.left != null) {
			Child_node.left.position = Main_node;
		}
		Child_node.position = Main_node.position;
		if (Main_node.position == null) {
			this.root = Child_node;
		} else {
			if (Main_node == Main_node.position.left)
				Main_node.position.left = Child_node;
			else
				Main_node.position.right = Child_node;
		}
		Child_node.left = Main_node;
		Main_node.position = Child_node;
	}

	private void rightRotate(RBNode Main_node) {
		RBNode Child_node = Main_node.left;
		Main_node.left = Child_node.right;

		if (Child_node.right != null) {
			Child_node.right.position = Main_node;
		}
		Child_node.position = Main_node.position;
		if (Main_node.position == null) {
			this.root = Child_node;
		} else {
			if (Main_node == Main_node.position.right)
				Main_node.position.right = Child_node;
			else
				Main_node.position.left = Child_node;
		}
		Child_node.right = Main_node;
		Main_node.position = Child_node;
	}

	public void insert(RBNode Main_node) {
		super.insert(Main_node);
		Main_node.color = RBTree.RED;
		while (Main_node != this.root && Main_node.position.color == RBTree.BLACK) {
			if (Main_node.position == Main_node.position.position.left) {
				RBNode y = Main_node.position.position.right;
				if (y.color == RBTree.RED) {
					Main_node.position.color = RBTree.BLACK;
					y.color = RBTree.BLACK;
					Main_node.position.position.color = RBTree.RED;
					Main_node = Main_node.position.position;
				} else {
					if (Main_node == Main_node.position.right) {
						Main_node = Main_node.position;
						this.leftRotate(Main_node);
					}
					Main_node.position.color = RBTree.BLACK;
					Main_node.position.position.color = RBTree.RED;
					this.rightRotate(Main_node.position.position);
				}
			} else {
				RBNode y = Main_node.position.position.left;
				if (y.color == RBTree.RED) {
					Main_node.position.color = RBTree.BLACK;
					y.color = RBTree.BLACK;
					Main_node.position.position.color = RBTree.RED;
					Main_node = Main_node.position.position;
				} else {
					if (Main_node == Main_node.position.left) {
						Main_node = Main_node.position;
						this.rightRotate(Main_node);
					}
					Main_node.position.color = RBTree.BLACK;
					Main_node.position.position.color = RBTree.RED;
					this.leftRotate(Main_node.position.position);
				}
			}
		}
		this.root.color = RBTree.BLACK;
	}

}
