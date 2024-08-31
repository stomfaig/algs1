## Module 10 interview questions.

### Red–black BST with no extra memory. 
**Describe how to save the memory for storing the color information when implementing a red–black BST.**
If a node has children, for red nodes exchange them: the left neighbor should be the larger, and the right neighbor the smaller. Thus, with a single if/node we can check if the link is red or black. One can use an indicator bit.

### Question 2 Document search.
**Design an algorithm that takes a sequence of n document words and a sequence of m query words and find the shortest interval in which the m query words appear in the document in the order given. The length of an interval is the number of words in that interval.**
There is an easy linear time solution in n. It seems that there might be another, where one first collects all the indices, where a given word appears, and then processes these lists, but I am not completely convinced that the result is a faster method.


### Generalized queue.
**Design a generalized queue data type that supports all of the following operations in logarithmic time (or better) in the worst case.**
Store the incoming data in a red-black BST. Upon the guarantees that we obtained for this data structure, it follows that all operations are going to be logarithmic.