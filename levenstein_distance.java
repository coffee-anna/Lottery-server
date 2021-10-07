package lottery;
import java.util.Arrays;

public class levenstein_distance 
{
	int compute_Levenshtein_distanceDP (String[] str1, String[] str2)
	{

	// A 2-D matrix to store previously calculated
	// answers of subproblems in order
	// to obtain the final
	
	int[][] dp = new int[str1.length + 1][str2.length + 1];
	
	for (int i = 0; i <= str1.length; i++) 
	{
		for (int j = 0; j <= str2.length; j++) {
	
			// If str1 is empty, all characters of
			// str2 are inserted into str1, which is of
			// the only possible method of conversion
			// with minimum operations.
			if (i == 0) {
				dp[i][j] = j;
			}
	
			// If str2 is empty, all characters of str1
			// are removed, which is the only possible
			//  method of conversion with minimum
			//  operations.
			else if (j == 0) {
				dp[i][j] = i;
			}
	
			else {
				// find the minimum among three
				// operations below
				dp[i][j] = minm_edits(dp[i - 1][j - 1]
				+ NumOfReplacement(str1[i - 1],str2[j - 1]), // replace
				dp[i - 1][j] + 1, // delete
				dp[i][j - 1] + 1); // insert
			}
		}
	}
	
	return dp[str1.length][str2.length];
	}
	
	
	static int NumOfReplacement(String c1, String c2)
	{
		return c1.equals(c2) ? 0 : 1;
	}
	
	// receives the count of different
	// operations performed and returns the
	// minimum value among them.
	
	static int minm_edits(int... nums)
	{	
		return Arrays.stream(nums).min().orElse(Integer.MAX_VALUE);
	}
	
	// Driver Code
	/*
	public static void main(String args[])
	{
		String[] s1 = {"23", "1", "10", "5"};
		String[] s2 = {"23", "2", "11", "5"};
		System.out.println(compute_Levenshtein_distanceDP(s1, s2));
	}
	*/
	
	
}
