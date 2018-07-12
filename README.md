# pwdEditText

Login password has the mix of  numbers and  characters, at least 8 digits. There are 70 points for meeting the basic password rule.
The rule of adding and substracting points is as follows:
 密码至少8位，数字、字母混合, 且数字至少2位. 符合密码基本规则者为70分，加减分规则如下：

• ＋(总字母数－8) × 4                                      
• ＋(total digits －8) × 4
例如，12位密码，可加16分                                  
for example, 12 digits password, add 16 points

• ＋(字母数字元数－大写字母数字元数) × (字母数字元数－小写字母数字元数) × 2               
•＋(number of characters－number of capital characters) × (number of characters－number of  characters in lowercase) × 2
例如，密码中有4个字母，其中1个大写字母，3个小写字母，可加 （4-1）*（4-3）*2 = 6分                                                       
For example, there are 4 characters in password, among which there is 1 capital character and 3  characters in lowercase. Add （4-1）*（4-3）*2 = 6 points

• －(接连重复(Repeat)字元数) × 2           
• －(number of repeated numbers) × 2
例如，密码中出现了 aa，222，则这一项应分别扣 4分和6分                                                                                                     
For example, aa and 222 in password. Substract 4 points and 6 points. 

• －(接连(Consecutive)数字字元数－3) × 1         
• －(number of consecutive numbers －3) × 1
例如，MS39621KYTY，这个密码出现了接连5位的数字，则这一项应扣（5-3）*1=2分                                                      
For example, in MS39621KYTY, there are 4 character numbers, thus, substract （5-3）*1=2 points 

• －(接连(Consecutive)字母字元数－3) × 1                    
• －(number of consecutive  characters－3) × 1
例如，MS39621KYTY，这个密码出现了接连4位的字母，则这一项应扣（4-3）*1=1分                                                       
For example, in MS39621KYTY, there are 4 consecutive characters, thus, substract （4-3）*1=1 points 

• －(3码以上的连续(sequential)数字) × 3          
• －(over 3 digits of sequential numbers) × 3
例如，MS4567KYTY123，这个密码出现了 4567和123两次三码以上的连续数字，则这一项应扣2*3=6分                              
For example, in MS4567KYTY123, over 3 digits of sequential numbers occur twice (4567, 123), therefore, substract 2*3=6 points

• －(3码以上的连续(sequential)字母) × 3           
• －(over 3 digits of sequential characters) × 3
例如，MS4345ABCD，这个密码出现了 ABCD一次三码以上的连续字母，则这一项应扣1*3=3分                                  
For example, in MS4345ABCD, over 3 digits of sequential characters occurs  (ABCD), therefore, substract 1 *3=3points

结论：基础分经过规则判断，总分＜60者为弱，60≤总分＜80为中，总分≥80者为强。                                                           
In conclusion, through basic rule, total points < 60 :Weak, 60≤ total points＜80: Medium,  total points ≥80 : Strong. 
