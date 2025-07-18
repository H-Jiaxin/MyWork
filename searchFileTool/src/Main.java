import com.SearchFileTool.SearchFileTool;
import com.SearchFileTool.SearchModel;

public class Main {
    private static void printError() {
        System.err.println("Usage: java Main <search-mod>[optional] <search-file-name> <search-path>");
        System.err.println("       <search-mod> could be [fuzzy, exact] and so on");
        System.err.println("       <search-file-name> is the name of the file to be searched.");
        System.err.println("       <search-path> is the path to the searched file.default current directory.");
        System.err.println("       note: you can use new feature with my provide threadPool to search file");
        System.err.println("             just add --thread in the end of args");
    }


    public static void main(String[] args) throws InterruptedException {
        int argsLength = args.length;
        boolean useThread = false;
        if (args[argsLength-1].equals("--thread")) {
            useThread = true;
            argsLength--;
        }
        switch (argsLength) {
            case 1:
                new SearchFileTool(SearchModel.fuzzy, useThread).Search(args[0]);
                break;
            case 2:
                new SearchFileTool(SearchModel.fuzzy, args[1], useThread).Search(args[0]);
                break;
            case 3:
                if (args[0].equals("exact")) new SearchFileTool(SearchModel.exact, args[2], useThread).Search(args[1]);
                else if (args[0].equals("fuzzy")) new SearchFileTool(SearchModel.fuzzy, args[2], useThread).Search(args[1]);
                else printError();
                break;
            default:
                printError();
                break;
        }
    }
}
