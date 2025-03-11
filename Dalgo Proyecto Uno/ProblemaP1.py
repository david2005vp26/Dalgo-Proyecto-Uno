import sys

def solucion(n, j, m, arr):
    # Crear una matriz 3D dp con valores iniciales de infinito
    dp = [[[float('inf')] * (m + 1) for _ in range(n + 1)] for _ in range(n + 1)]
    
    for i in range(n + 1):
        for s in range(j + 1):
            for k in range(m + 1):
                if i == 0:
                    dp[i][s][k] = 0
                elif i == s:
                    dp[i][s][k] = sum(arr[:i])  # Solo suma los primeros i elementos
                elif k == 0:
                    dp[i][s][k] = sumatoria(s, arr)
                elif s > i:
                    dp[i][s][k] = float('inf')
                elif k >= (i - s):
                    dp[i][s][k] = min(dp[i - 1][s][k], dp[i - 1][s - 1][k - (i - s)] + arr[i - 1])
                elif k < (i - s):
                    dp[i][s][k] = dp[i - 1][s][k]
                
    
    return dp[n][j][m]

def sumatoria(j, arr):
    suma = 0
    i = 0
    while i<j :
        suma += arr[i]
        i += 1
    return suma

#print(solucion(23, 11, 19, [127, 103, 1, 23, 81, 43, 61, 153, 181, 47, 7, 3, 27, 91, 43, 57, 21, 1, 73, 13, 13, 1, 31]))
def procesar_casos():
    # Leer la cantidad de casos de prueba
    t = int(sys.stdin.readline().strip())

    for _ in range(t):
        # Leer valores de n, j, m y la lista de enteros
        entrada = list(map(int, sys.stdin.readline().strip().split()))
        n, j, m = entrada[:3]  
        arr = entrada[3:] 
        
        print(solucion(n, j, m, arr))

if __name__ == "__main__":
    procesar_casos()