public class Test {
    public static int iteracions = 0;
    public static void main(String args[]){
        int[] a = genArray(50000000);
        busqueda_dicotomica(a, 0, a.length, a[(int) (Math.random()*a.length)]);
        System.out.println("Iteracions: " + iteracions);
    }

    public static int[] genArray(int n){
        int[] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = (int) (Math.random() * 100);
        }
        return a;
    }

    public static boolean busqueda_dicotomica(int[] v, int principio, int fin, int x){
        boolean res;
        iteracions++;
        if(principio <= fin){
            int m = ((fin - principio)/2) + principio;
            if(x < v[m]) res = busqueda_dicotomica(v, principio, m-1, x);
            else if(x > v[m]) res = busqueda_dicotomica(v, m+1, fin, x);
            else res = true;
        }else res = false;
        return res;
    }
}