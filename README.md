# parasoles-fotovoltaicos
Simulación de producción instantánea e integrada en el tiempo de parasoles fotovoltaicos.

Basado fundamentalmente en el sombreado mutuo de filas adyacentes.

+ Archivo matriz.java: define clase con métodos para trabajar con datos vectorizados.

+ Funcion generaG0Diario: genera los valores de Gd0 de cada mes y dia
	+ carga matriz de transicion[10][100]
	+ genera intervalos de la matriz: kt_max,kt_min,dkt
	+ calcula ktm de los valores del dia representativo del mes
	+ comprueba que el valor medio mensual tiene un error <5%

+ Funcion calcIrradiacion: genera los valores de Gd0 de cada mes
