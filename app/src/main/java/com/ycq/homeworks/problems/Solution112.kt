package com.ycq.homeworks.problems

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */

class TreeNode(var value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Solution {
    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        val current = root ?: return false
        if (current.left == null && current.right == null) {
            if (targetSum == current.value) {
                return true
            }
            return false
        }
        if (hasPathSum(current.left, targetSum - current.value)) {
            return true
        }
        if (hasPathSum(current.right, targetSum - current.value)) {
            return true
        }

        return false
    }

}