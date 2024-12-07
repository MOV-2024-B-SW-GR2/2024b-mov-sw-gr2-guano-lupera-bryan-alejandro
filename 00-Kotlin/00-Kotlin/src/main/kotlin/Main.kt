package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
//    calcularSueldo(10.00)
//    calcularSueldo(10.00, 15.00, 20.00)
//
//    calcularSueldo(10.00, bonoEspecial = 20.00)
//
//    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)


//    val sumaA = Suma(1,1)
//    val sumaB = Suma(null,2)
//    val sumaC = Suma(2,null)
//    val sumaD = Suma(null,null)
//
//    sumaA.sumar()
//    sumaB.sumar()
//    sumaC.sumar()
//    sumaD.sumar()
//
//    println(Suma.pi)
//    println(Suma.elevarAlCuadrado(2))
//    println(Suma.historialSumas)



//    Arreglos estáticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

//    Arregls dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

//    iterar y obtener el valor
    val respuestaForEach: Unit = arregloDinamico.forEach{
        valorActual: Int -> println("Valor actual: ${valorActual}")
    }

//    iterar sin obtener el valor
    arregloDinamico.forEach{ println("Valor Actual (it): ${it}") }

//    Map nos permite transformar el arrayList de Int a Double
    val respuestaMap: List<Double> = arregloDinamico.map{
        valorActual: Int ->
        return@map valorActual.toDouble() + 100.00
    }
    println(respuestaMap)

//    también se puede usar para cambiar todos los elementos dentro del
//    array
    val respuestaMapDos = arregloDinamico.map{it + 15}
    println(respuestaMapDos)


//   con filter se puede filtrar los elementos y devuelve un array
//   de los elementos que pasan el filtro
    val respuestaFilter: List<Int> = arregloDinamico.filter{
        valorActual:Int ->
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }

//    Otra sintaxis más concisa
    val respuestaFilterDos = arregloDinamico.filter{ it <= 5}
    println(respuestaFilter)
    println(respuestaFilterDos)


//    Any comprueba si al menos un elemento cumple la condición,
//    devuelve true si es así
    val respuestaAny: Boolean = arregloDinamico.any{
        valorActual: Int ->
        return@any (valorActual > 5)
    }
    println(respuestaAny)

//    All comprueba si todos los elementos cumplen la condición
    val respuestaAll: Boolean = arregloDinamico.all{
        valorActual: Int ->
        return@all (valorActual > 5)
    }
    println(respuestaAll)

//    Reduce sirve para realizar acciones acumulativas,
//    como sumar valor tras valor
    val respuestaReduce: Int = arregloDinamico.reduce{
        acumulado: Int, valorActual: Int ->
        return@reduce (acumulado + valorActual)
    }
    println(respuestaReduce)
}

fun calcularSueldo(
    sueldo: Double,
    tasa: Double = 12.00,
    bonoEspecial: Double? = null
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    protected val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}


abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parametroNoUsadoNoPropiedaddeLaClase: Int? = null
) {
    init {
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma(
    unoParametro: Int,
    dosParametro: Int,
) : Numeros(
    unoParametro,
    dosParametro
) {
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicite: String = "Publico implicito"

    init {
        this.numeroUno
        this.numeroDos
        numeroUno
        numeroDos
    }

    constructor(
        uno: Int?,
        dos: Int,
    ) : this(
        if (uno == null) 0 else uno,
        dos
    ) {
//    Constructor secundario
    }

    constructor(
        uno: Int,
        dos: Int?,
    ) : this(
        dos,
        if (dos == null) 0 else dos
    ) {
//    Constructor secundario
    }

    constructor(
        uno: Int?,
        dos: Int?
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    ) {
//    Constructor secundario
    }

    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14159

        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSumaL: Int) {
            historialSumas.add(valorTotalSumaL)
        }
    }
}
