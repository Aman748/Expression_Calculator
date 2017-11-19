package discrete_mathematics;
/**
 *
 * @author Aman
 */
import java.util.*;
import java.util.regex.*;
public class Discrete_Mathematics {
    public static boolean check(String s) {
        int bracket_num = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                bracket_num++;
            else if (s.charAt(i) == ')')
                bracket_num--;
        }
        if (bracket_num != 0)
            return false;
        String[] table =
                {"[^A-Za-z\\(\\)!&|^~]", "[^\\w\\(\\)]$", "^[^\\w!\\(\\)]",
                        "[^\\w\\(\\)][^!\\w\\(\\)]", "[^\\w\\)][^!\\w\\(]", "[\\w\\)][\\w\\(!]"};
        for (String regEx : table) {
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(s);
            if (matcher.find())
                return false;
        }
        return true;
    }

    public static String postfix(String s) {
        Stack out = new Stack();
        Stack ope = new Stack();
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                out.push(s.charAt(i));
            } else {
                if (ope.empty()) {
                    ope.push(s.charAt(i));
                } else {
                    switch (s.charAt(i)) {
                        case '(':
                            ope.push(s.charAt(i));
                            break;
                        case ')':
                            while (!ope.peek().equals('(')) {
                                out.push(ope.pop());
                            }
                            ope.pop();
                            break;
//                        case '~':
//                            while (ope.peek().equals('!') || ope.peek().equals('&') || ope.peek().equals('|')
//                                    || ope.peek().equals('^') || ope.peek().equals('~')) {
//                                out.push(ope.pop());
//                                if (ope.empty())
//                                    break;
//                            }
//                            ope.push(s.charAt(i));
//                            break;
                        case '~':
                            while (ope.peek().equals('!') || ope.peek().equals('^')
                                    || ope.peek().equals('|') || ope.peek().equals('~')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '|':
                            while (ope.peek().equals('!') || ope.peek().equals('^') || ope.peek().equals('|')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '^':
                            while (ope.peek().equals('!') || ope.peek().equals('^')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '!':
                            ope.push(s.charAt(i));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        while (!ope.empty()) {
            out.push(ope.pop());
        }
        Stack temp = new Stack();
        while (!out.empty()) {
            temp.push(out.pop());
        }
        String _out = "";
        while (!temp.empty()) {
            _out += temp.pop();
        }
        return _out;
    }

    public static String prefix(String s) {
        Stack out = new Stack();
        Stack ope = new Stack();
        for (int i = s.length() - 1; i >= 0; i--) {
            if ((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                out.push(s.charAt(i));
            } else {
                if (ope.empty()) {
                    ope.push(s.charAt(i));
                } else {
                    switch (s.charAt(i)) {
                        case ')':
                            ope.push(s.charAt(i));
                            break;
                        case '(':
                            while (!ope.peek().equals(')')) {
                                out.push(ope.pop());
                            }
                            ope.pop();
                            break;
                        case '=':
                            while (ope.peek().equals('!') || ope.peek().equals('^')
                                    || ope.peek().equals('|') || ope.peek().equals('~')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '~':
                            while (ope.peek().equals('!') || ope.peek().equals('^') || ope.peek().equals('|')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '|':
                            while (ope.peek().equals('!') || ope.peek().equals('^')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '^':
                            while (ope.peek().equals('!')) {
                                out.push(ope.pop());
                                if (ope.empty())
                                    break;
                            }
                            ope.push(s.charAt(i));
                            break;
                        case '!':
                            ope.push(s.charAt(i));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        while (!ope.empty()) {
            out.push(ope.pop());
        }
        String _out = "";
        while (!out.empty()) {
            _out += out.pop();
        }
        return _out;
    }

    public static String table(String input) {
        String output = "";
        String list = "";
        String s = postfix(input);
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))
                if (list.indexOf(s.charAt(i)) == -1)
                    list += s.charAt(i);
        }
        int[] status = new int[list.length()];
        for (int i = 0; i < list.length(); i++) {
            output += list.charAt(i) + "     ";
        }
        output += input + "\n";
        for (int i = 0; i < Math.pow(2, list.length()); i++) {
            for (int j = 0; j < list.length(); j++) {
                output += status[j] + "     ";
            }
            Stack temp = new Stack();
            for (int k = 0; k < s.length(); k++) {
                if ((s.charAt(k) >= 'a' && s.charAt(k) <= 'z') || (s.charAt(k) >= 'A' && s.charAt(k) <= 'Z')) {
                    temp.push(status[list.indexOf(s.charAt(k))]);
                } else switch (s.charAt(k)) {
                    case '^':
                        temp.push((int) temp.pop() & (int) temp.pop());
                        break;
                    case '|':
                        temp.push((int) temp.pop() | (int) temp.pop());
                        break;
                    case '!':
                        int z = (int) temp.pop();
                        if (z == 1)
                            temp.push(0);
                        else
                            temp.push(1);
                        break;
//                    case '~':
//                        int v = (int) temp.pop();
//                        int w = (int) temp.pop();
//                        if (v == w)
//                            temp.push(1);
//                        else
//                            temp.push(0);
//                        break;
                    case '~':
                        int x = (int) temp.pop();
                        int y = (int) temp.pop();
                        if (x == 0 && y == 1)
                            temp.push(0);
                        else
                            temp.push(1);
                        break;
                }
            }
            output += temp.pop() + "\n";
            int q = list.length() - 1;
            while (true) {
                if (q < 0)
                    break;
                if (status[q] == 1)
                    status[q--] = 0;
                else {
                    status[q] = 1;
                    break;
                }
            }
        }
        return output;
    }

    public static String conjunctive(String input) {
        String list = "";
        String _out = "";
        String s = postfix(input);
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))
                if (list.indexOf(s.charAt(i)) == -1)
                    list += s.charAt(i);
        }
        int[] status = new int[list.length()];
        int count = 0;
        for (int i = 0; i < Math.pow(2, list.length()); i++) {
            Stack temp = new Stack();
            for (int k = 0; k < s.length(); k++) {
                if ((s.charAt(k) >= 'a' && s.charAt(k) <= 'z') || (s.charAt(k) >= 'A' && s.charAt(k) <= 'Z')) {
                    temp.push(status[list.indexOf(s.charAt(k))]);
                } else switch (s.charAt(k)) {
                    case '^':
                        temp.push((int) temp.pop() & (int) temp.pop());
                        break;
                    case '|':
                        temp.push((int) temp.pop() | (int) temp.pop());
                        break;
                    case '!':
                        int z = (int) temp.pop();
                        if (z == 1)
                            temp.push(0);
                        else
                            temp.push(1);
                        break;
//                    case '~':
//                        int v = (int) temp.pop();
//                        int w = (int) temp.pop();
//                        if (v == w)
//                            temp.push(1);
//                        else
//                            temp.push(0);
//                        break;
                    case '~':
                        int x = (int) temp.pop();
                        int y = (int) temp.pop();
                        if (x == 0 && y == 1)
                            temp.push(0);
                        else
                            temp.push(1);
                        break;
                }
            }
            int output = (int) temp.pop();
            String table1 = "";
            String table2 = "";
            if (output == 0) {
                count++;
                for (int q = 0; q < list.length() - 1; q++) {
                    if (status[q] == 0)
                        table2 += list.charAt(q) + "|";
                    else
                        table2 += "!" + list.charAt(q) + "|";
                }
                if (status[list.length() - 1] == 0)
                    table2 += list.charAt(list.length() - 1);
                else
                    table2 += "!" + list.charAt(list.length() - 1);
                table2 = "(" + table2 + ")";
                _out += table2 + "^";
            }
            int q = list.length() - 1;
            while (true) {
                if (q < 0)
                    break;
                if (status[q] == 1)
                    status[q--] = 0;
                else {
                    status[q] = 1;
                    break;
                }
            }
        }
        if (count == 0)
            _out += "NULL";
        else {
            _out = _out.substring(0, _out.length() - 1);
            if (count == 1)
                _out = _out.substring(1, _out.length() - 1);
        }
        return _out;
    }

    public static String disjunctive(String input) {
        String list = "";
        String _out = "";
        String s = postfix(input);
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))
                if (list.indexOf(s.charAt(i)) == -1)
                    list += s.charAt(i);
        }
        int[] status = new int[list.length()];
        int count = 0;
        for (int i = 0; i < Math.pow(2, list.length()); i++) {
            Stack temp = new Stack();
            for (int k = 0; k < s.length(); k++) {
                if ((s.charAt(k) >= 'a' && s.charAt(k) <= 'z') || (s.charAt(k) >= 'A' && s.charAt(k) <= 'Z')) {
                    temp.push(status[list.indexOf(s.charAt(k))]);
                } else switch (s.charAt(k)) {
                    case '^':
                        temp.push((int) temp.pop() & (int) temp.pop());
                        break;
                    case '|':
                        temp.push((int) temp.pop() | (int) temp.pop());
                        break;
                    case '!':
                        int z = (int) temp.pop();
                        if (z == 1)
                            temp.push(0);
                        else
                            temp.push(1);
                        break;
//                    case '~':
//                        int v = (int) temp.pop();
//                        int w = (int) temp.pop();
//                        if (v == w)
//                            temp.push(1);
//                        else
//                            temp.push(0);
//                        break;
                    case '~':
                        int x = (int) temp.pop();
                        int y = (int) temp.pop();
                        if (x == 0 && y == 1)
                            temp.push(0);
                        else
                            temp.push(1);
                        break;
                }
            }
            int output = (int) temp.pop();
            String table1 = "";
            String table2 = "";
            if (output == 1) {
                count++;
                for (int q = 0; q < list.length() - 1; q++) {
                    if (status[q] == 1)
                        table1 += list.charAt(q) + "^";
                    else
                        table1 += "!" + list.charAt(q) + "^";
                }
                if (status[list.length() - 1] == 1)
                    table1 += list.charAt(list.length() - 1);
                else
                    table1 += "!" + list.charAt(list.length() - 1);
                table1 = "(" + table1 + ")";
                _out += table1 + "|";
            }
            int q = list.length() - 1;
            while (true) {
                if (q < 0)
                    break;
                if (status[q] == 1)
                    status[q--] = 0;
                else {
                    status[q] = 1;
                    break;
                }
            }
        }
        if (count == 0)
            _out += "NULL";
        else {
            _out = _out.substring(0, _out.length() - 1);
            if (count == 1)
                _out = _out.substring(1, _out.length() - 1);
        }
        return _out;
    }

    public static String build(ArrayList input) {
        String s = (String) input.get(0);
        String[] table = s.split("[ \n]");
        int count = 0;
        String out = "";
        for (int i = 1; i < Math.pow(2, table.length); i++) {
            String state = "";
            String line = (String) input.get(i);
            for (int j = 0; j < table.length - 1; j++) {
                int temp = line.charAt(2 * j) - '0';
                if (temp == 1)
                    state += table[j] + "^";
                else
                    state += "!" + table[j] + "^";
            }
            if (line.charAt(2 * (table.length - 1)) - '0' == 1)
                state += table[table.length - 1] + ")";
            else
                state += "!" + table[table.length - 1] + ")";
            state = "(" + state + "|";
            if (line.charAt(2 * table.length) - '0' == 1) {
                out += state;
                count++;
            }
        }
        String state = "";
        String line = (String) input.get((int) Math.pow(2, table.length));
        for (int k = 0; k < table.length - 1; k++) {
            int temp = line.charAt(2 * k) - '0';
            if (temp == 1)
                state += table[k] + "^";
            else
                state += "!" + table[k] + "^";
        }
        if (line.charAt(2 * (table.length - 1)) - '0' == 1)
            state += table[table.length - 1] + ")";
        else
            state += "!" + table[table.length - 1] + ")";
        state = "(" + state;
        if (line.charAt(2 * table.length) - '0' == 1) {
            out += state;
            count++;
        }
        if (out.charAt(out.length() - 1) == '|')
            out = out.substring(0, out.length() - 1);
        if (count == 1)
            out = out.substring(1, out.length() - 1);
        return out;
    }
}