import java.util.*;

public class Main2 {
    public static int  countOccur(String parent, String sub)
    {
        int  answer = 0;
        // Write your code here
        if(parent == null || sub == null || parent.length() == 0 || sub.length() == 0){
            return answer;
        }
        answer = (parent.length() - parent.replace(sub, "").length()) / sub.length();

        Map<String, Object> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        

        return answer;
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        // input for parent
        String parent = in.nextLine();

        // input for sub
        String sub = in.nextLine();

        int result = countOccur(parent, sub);
        System.out.print(result);

    }
}
