import java.util.*;

public class ProblemaP1 {
    static final int INF = 1000000000; // Valor suficientemente grande

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt(); // Número de casos de prueba
        while (T-- > 0) {
            int n = sc.nextInt();
            int j = sc.nextInt();  // Número de elementos a seleccionar
            int m = sc.nextInt();  // Número máximo de swaps permitidos
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            System.out.println(solve(n, j, m, arr));
        }
        sc.close();
    }

    // Función que resuelve el problema utilizando DP en dos capas (prev y curr)
    static int solve(int n, int j, int m, int[] arr) {
        // Usamos dos arreglos 2D: prev[s][x] y curr[s][x]
        // s: cantidad de elementos seleccionados (0..n)
        // x: número de swaps usados (0..m)
        int[][] prev = new int[n + 1][m + 1];
        int[][] curr = new int[n + 1][m + 1];

        // Inicializamos prev con INF
        for (int s = 0; s <= n; s++) {
            Arrays.fill(prev[s], INF);
        }
        // Caso base: con 0 elementos, solo s = 0 es posible y su costo es 0
        for (int x = 0; x <= m; x++) {
            prev[0][x] = 0;
        }
        
        // Precomputamos el arreglo de prefijos: prefix[i] = sum(arr[0..i-1])
        int[] prefix = new int[n + 1];
        prefix[0] = 0;
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + arr[i - 1];
        }
        
        // Iteramos para i = 1 hasta n (considerando los primeros i elementos)
        for (int i = 1; i <= n; i++) {
            // Inicializamos la capa actual con INF
            for (int s = 0; s <= n; s++) {
                Arrays.fill(curr[s], INF);
            }
            // Caso base: s = 0 siempre es 0
            for (int x = 0; x <= m; x++) {
                curr[0][x] = 0;
            }
            // Para s de 1 hasta i (recordemos que s > i no es posible, y lo dejamos en INF)
            for (int s = 1; s <= i; s++) {
                if (s == i) {
                    // Si se deben seleccionar todos los i elementos, el costo es la suma de ellos.
                    for (int x = 0; x <= m; x++) {
                        curr[s][x] = prefix[i];
                    }
                } else {
                    int cost = i - s; // swaps necesarios para "mover" arr[i-1] a la posición s
                    for (int x = 0; x <= m; x++) {
                        if (x < cost) {
                            // No hay suficientes swaps: no podemos elegir arr[i-1]
                            curr[s][x] = prev[s][x];
                        } else {
                            // Opción 1: No se incluye arr[i-1]
                            int op1 = prev[s][x];
                            // Opción 2: Se incluye arr[i-1] consumiendo 'cost' swaps
                            int op2 = prev[s - 1][x - cost] + arr[i - 1];
                            curr[s][x] = Math.min(op1, op2);
                        }
                    }
                }
            }
            // Copiamos curr a prev para la siguiente iteración
            for (int s = 0; s <= n; s++) {
                System.arraycopy(curr[s], 0, prev[s], 0, m + 1);
            }
        }
        // La respuesta final es dp(n, j, m), almacenado en prev[j][m]
        return prev[j][m];
    }
}
