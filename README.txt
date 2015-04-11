Author: Jordan Harris
Date: 4/6/2015

IDE used: JetBrains IntelliJ IDEA 14

Data Structures Used: Array
Search Algorithms Used: Linear Search

Data Structure:
An 2-dimensional array was used as a data structure to hold the edit distance between each character in the two strings. This data structure was chosen in order to store primitive data types as well as allow for ease of iterations.

Class files:
LCSMain - contains the entire program

n = number of characters in first string
m = number of characters in second string

Time Complexity:
	Reading Input
		T(n, m) = n + m = O(n + m)
	Calculating Edit Distance
		T(n, m) = (n+1)(m+1) = O(nm)
	Calculating LCS
		T(n, m) = 2(n+1)(m+1) + n = O(nm)
	Overall Program
		T(n, m) = 3(n+1)(m+1) + 2n + m = O(nm)

Space Complexity (arrays):
	Edit Distance
		S(n, m) = 2(n+1) = O(n)
	LCS
		S(n, m) = (n+1)(m+1) = O(nm)
	Overall Program
		S(n, m) = (n+1)(m+1) + 2(n+1) = O(nm)

User input: Name of files to read input, name of file for output

Overview:
This program calculates the Normalized Edit Distance and the Longest Common Subsequence (LCS) of two strings. Each string should be the first line in a text file. The program asks the user to enter the names of the two files, then the program reads the strings. The program calculates the Normalized Edit Distance using linear memory by using a table with only 2 rows. It then calculates the LCS using a table which is the size of one string by the size of the other string (n x m). These results are output to a text file entered by the user.

Reading Input:
The program asks the user to enter the name of two files to read. The program opens and reads through each file once and appends the characters to a string. This operation takes time T(n) = n + m = O(n + m) since each character of each string is read through once.

Computing Edit Distance:
By convention, the first string read is designated the left string, and the second string read is designated the top string for constructing a table with a 2-dimensional array. Therefore the array is 2 rows by length of string 2 columns. The edit distance is calculated by instatiating the first row, then using the instructions provided to create the second row over each iteration. Then the second row is copied to the first row, and the operation is repeated until every character has been checked and edit distance calculated. This operation takes time T(n, m) = (n+1)(m+1) = O(nm) and space S(n, m) = 2(n+1) = O(n).

Computing LCS:
The array created for the LCS was very similar to the array creating for calculating the normalized edit distance, except that it is length of string 1 rows by length of string 2 columns. The edit distance for each character is calculated, so the operation takes time T(n, m) = (n+1)(m+1) = O(nm) and space S(n, m) = (n+1)(m+1) = O(nm). Using the logic provided in the instructions, the program then iterates back over the table. When characters match, it adds that character to a stack. When they don't, it moves to the smaller value of the top row or left column. This continues until the indices run off the table. This takes time T(n, m) = n = O(n) if n is larger or T(n, m) = m = O(m) if m is larger. Each character is then popped off the stack and added to a string, which takes time T(n, m) = n = O(n) if n is larger or T(n, m) = m = O(m) if m is larger. This string is the LCS, which is returned.

Outputing Results to File
The Normalized Edit Distance and LCS are output to a file, which takes constant time.

Overall, this program has a time complexity of O(nm), and it has a space complexity of O(nm).

Strengths:
This program is able to consistently and correctly calculate the Normalized Edit Distance and LCS of two strings. It also provides input validation for the files it reads. In addition, it is able to calculated the Normalized Edit Distance with linear memory.

Limitations/Weaknesses:
This program does not use linear memory for calculating the LCS (extra credit), which would reduce the overall memory usage. In addition, this program doesn't provide additional validation/testing and does not take advantage of object oriented design for providing an interface to other users who might want to use this program.