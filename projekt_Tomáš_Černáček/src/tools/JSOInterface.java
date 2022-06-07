package tools;

interface JSOInterface {
    int availableIndex(String k);
    int availableIndex(String k, int n);
    int checkEmployeeID(int id);
    boolean exist(String name, String k);
    String getProductTime(String name, String k);
    int getProductAmount(String name, String k);
}

