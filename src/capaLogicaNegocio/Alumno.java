
package capaLogicaNegocio;

import capaDatos.ControladorAlumno;
import capaDatos.DataAccessObject;
import capaInterfaz.listados.ListadoAlumno;
import capaInterfaz.listados.ListadoEvaluacion;
import capaInterfaz.listados.ListadoHistoricoAlumno;
import java.util.List;

/** Clase de la capa de Negocio que gestiona todo lo referente a los alumnos.
 *
 *
 * @author Confiencial
 */

public class Alumno {

    private String dni = null;
    private String nombre = null;
    private String apellidos = null;
    private String n_mat = null;

    /** Crea un nuevo alumno e inicializa los atributos dni, nombre, apellidos
     *  y n� de matr�cula.
     *
     * @param dni dni del alumno
     * @param nombre nombre del alumno
     * @param apellidos apellidos del alumno
     * @param N_Mat N�mero de matr�cula del alumno
     */

    public Alumno (String dni, String nombre, String apellidos, String N_Mat){

        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.n_mat = N_Mat;
    } // fin del constructor

    /** Crea un alumno e inicializa los atributos dni y n� de matr�cula.
     *
     * @param dni dni del alumno
     * @param num_matricula N�mero de matr�cula del alumno
     */
    public Alumno (String num_matricula, String dni) {
        this.n_mat = num_matricula;
        this.dni = dni;
    } // fin del constructor


    /** Crea un alumno e inicializa el atributo dni.
     *
     * @param dni dni del alumno
     */
    public Alumno (String dni){

        this.dni = dni;
    } // fin del constructor

    /** Crea un alumno.
     */
    public Alumno () {
    }


   /** M�todo que comprueba si un alumno no est� dado de alta en la Base de Datos.
     *
     * @return TRUE si el alumno no est� dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre alg�n error, se lanzar� una excepci�n.
     */
    public boolean noEstaDadoDeAlta() {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        return (! DAOAlumno.estaDadoDeAlta(this));
    } // fin del m�todo noEstaDadoDeAlta

   /** M�todo que comprueba si un alumno est� dado de alta en la Base de Datos.
     *
     * @return TRUE si el alumno est� dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre alg�n error, se lanzar� una excepci�n.
     */
    public boolean estaDadoDeAlta() {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        return (DAOAlumno.estaDadoDeAlta(this));
    } // fin del m�todo estaDadoDeAlta


    /** M�todo que comprueba si un alumno est� dado de alta en la Base de Datos
     *  en el curso actual.
     *
     * @return TRUE si el alumno est� dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre alg�n error, se lanzar� una excepci�n.
     */
    private boolean estaDadoDeAltaEnCursoActual() {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        return (DAOAlumno.estaDadoDeAltaEnCursoActual(this));
    } // fin del m�todo estaDadoDeAltaEnCursoActual



    /** M�todo que comprueba si un n� de matr�cula est� dado de alta en la 
     *  Base de Datos.
     *
     * @return TRUE si el n� de matr�cula est� dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre alg�n error, se lanzar� una excepci�n.
     */
    private boolean numMatriculaEstaDadaDeAlta() {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        return (DAOAlumno.numMatriculaEstaDadaDeAlta(this));
    } // fin del m�todo numMatriculaEstaDadaDeAlta

    
    /** M�todo que comprueba si un n� de matr�cula est� dado de alta en la 
     *  Base de Datos en el curso actual.
     *
     * @return TRUE si el n� de matr�cula est� dado de alta.
     *         FALSE en caso contrario.
     *         Si ocurre alg�n error, se lanzar� una excepci�n.
     */
    private boolean numMatriculaEstaDadaDeAltaEnCursoActual() {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        return (DAOAlumno.numMatriculaEstaDadaDeAltaEnCursoActual(this));
    } // fin del m�todo numMatriculaEstaDadaDeAlta


    /** M�todo que realiza el alta masiva de alumnos (recibidos en un fichero txt).
     *  Si ocurre alg�n error, lanzar� una excepci�n.
     *
     * @param alumnos contiene los alumnos que se quieren dar de alta.
     */
    public void altaMasivaAlumnos(List<ListadoAlumno> alumnos) {
        
        if (alumnos == null) {
            throw new RuntimeException("El fichero est� vac�o");
        } else {
            Convocatoria convocatoria = new Convocatoria ();
            String convocatoria_actual = convocatoria.getConvocatoriaActual();
            GrupoClase grupo_clase = null;
            Alumno alumno = null;
            if (convocatoria_actual.equals("ordinaria")) {
                for (int i=0; i < alumnos.size() ; i++) {
                    grupo_clase = new GrupoClase(alumnos.get(i).getGrupoClase());
                    alumno = new Alumno (alumnos.get(i).getDNI(),
                                         alumnos.get(i).getNombre(),
                                         alumnos.get(i).getApellidos(),
                                         alumnos.get(i).getNumMatricula());
                    alumno.altaAlumno(grupo_clase);
                }
            } 
        }
    } // fin del m�todo altaMasivaAlumnos



    /** M�todo que realiza el alta de un alumno ya sea al matricularse en
     *  el curso (convocatoria ordinaria) como para darle de alta en la convocatoria
     *  extraordinaria en caso de suspender la asignatura.
     *  Si ocurre alg�n error, lanzar� una excepci�n.
     *
     * @param grupo_clase grupo de clase en el que se matricula el alumno
     */
    public void altaAlumno (GrupoClase grupo_clase) {
        
        this.validarCampos(grupo_clase);
        Curso curso = new Curso ();
        Convocatoria convocatoria = new Convocatoria ();
        String convocatoria_actual = convocatoria.getConvocatoriaActual();
        Evaluacion evaluacion = new Evaluacion (this.dni,curso.getCursoActual(),
                                                convocatoria_actual);
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            if (convocatoria_actual.equals("ordinaria")) {
                Matricula matricula = new Matricula (this.dni, grupo_clase, curso.getCursoActual());
                DAOAlumno.darAltaAlumnoConvocatoriaOrdinaria(this, matricula,
                                                             evaluacion, dataAccessObject);
            } else {
                DAOAlumno.darAltaAlumnoConvocatoriaExtraordinaria(evaluacion, dataAccessObject);
            }
            dataAccessObject.close();
        } catch(RuntimeException e) {
            dataAccessObject.rollback();
            throw new RuntimeException(e.getMessage());
        }
    } // fin del m�todo altaAlumno


    /** M�todo que comprueba que los campos no est�n vac�os.
     *
     * @param grupo_clase contiene la informaci�n del grupo de clase en el que
     *        se da de alta a un alumno.
     *        Si alguno de los campos est� vac�o, lanzar� una excepci�n.
     */
    private void validarCampos(GrupoClase grupo_clase) {
        if (this.estaVacio(dni)) {
            throw new RuntimeException("Rellene el campo DNI");
        } else {
            if (this.estaDadoDeAltaEnCursoActual()) {
                throw new RuntimeException("El alumno con DNI "+dni+" ya est� dado de alta");
            }
        }

        if (this.estaVacio(n_mat)) {
            throw new RuntimeException("Rellene el campo N� matr�cula");
        } else {
            if (this.numMatriculaEstaDadaDeAltaEnCursoActual()){
                throw new RuntimeException("El n� de matr�cula "+n_mat+" ya est� en uso");
            }
        }

        if (this.estaVacio(nombre)) {
            throw new RuntimeException("Rellene el campo Nombre");
        }

        if (this.estaVacio(apellidos)) {
            throw new RuntimeException("Rellene el campo Apellidos");
        }

        if (this.estaVacio(grupo_clase.getCodGrupoClase())) {
            throw new RuntimeException("Rellene el campo Grupo clase");
        }
    }  // fin del m�todo validarCampos



    /** M�todo que comprueba si el alumno est� dado de alta y, si lo est�,
     * llama al m�todo darBajaAlumno de la clase ControladorAlumno para
     * realizar la baja del mismo. Si ocurre alg�n error, lanzar� una excepci�n.
     */
    public void bajaAlumno () {
        ControladorAlumno DAOAlumno = new ControladorAlumno ();
        if (DAOAlumno.estaDadoDeAlta(this)) {
            DAOAlumno.darBajaAlumno(this);
        } else {
            throw new RuntimeException("el alumno no est� dado de alta");
         }
    } // fin del m�todo bajaAlumno
  

    /** M�todo que actualiza los datos de los alumnos recibidos por par�metro.
     * 
     * @param resultado_consulta contiene la informaci�n actualizada de los alumnos.
     */
    public void actualizarAlumnos(List<ListadoAlumno> resultado_consulta,
                                  String dni_antiguo[]) {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        Alumno alumno;
        GrupoClase grupo_clase;
        try {
            for (int i = 0; i < resultado_consulta.size(); i++){
                 alumno = new Alumno(resultado_consulta.get(i).getDNI(),
                                     resultado_consulta.get(i).getNombre(),
                                     resultado_consulta.get(i).getApellidos(),
                                     resultado_consulta.get(i).getNumMatricula());
                 grupo_clase = new GrupoClase(resultado_consulta.get(i).getGrupoClase());
                 DAOAlumno.actualizarAlumno(alumno, grupo_clase, dni_antiguo[i],
                                            dataAccessObject);
            }
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            throw new RuntimeException("Ha ocurrido un error durante la actualizaci�n.");
        }
    } // fin del m�todo actualizarAlumnos


    /** M�todo que realiza la consulta solicitada y devuelve el resultado.
     *
     * @param grupo_clase contiene la informaci�n del grupo de clase introducido
     *        en la consulta. Puede ser null.
     *
     * @return Si la ejecuci�n ha sido correcta, devuelve el resultado de la consulta.
     *         Si ocurre alg�n error, lanzar� una excepci�n.
     */
    public List<ListadoAlumno> consultarAlumno(GrupoClase grupo_clase) {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        return DAOAlumno.realizarConsultaAlumno(this, grupo_clase);
    } // fin del m�todo consultarAlumno


    /** M�todo que comprueba las notas obtenidas por los alumnos en convocatoria
     *  ordinaria y, posteriormente, da de alta en la convocatoria extraordinaria
     *  a aquellos alumnos suspensos en convocatoria ordinaria.
     *  Si ocurre alg�n error, lanzar� una excepci�n.
     *
     * @param resultado contiene todas las evaluaciones de los alumnos matriculados
     *        en el curso actual.
     */
    public void cambioAConvocatoriaExtraordinaria(List<ListadoEvaluacion> resultado) {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        DAOAlumno.cambioAConvocatoriaExtraordinaria(resultado);
    } // fin del m�todo cambioAConvocatoriaExtraordinaria



    /** M�todo que realiza la consulta solicitada y devuelve el resultado.
     *
     * @param dni_alumno contiene el dni del alumno por el cual se quiere filtrar
     *        la consulta. Puede ser null.
     * @param convocatoria convocatoria por la cual se quiere filtrar la consulta.
     *        Puede ser null.
     * @param curso curso por el cual se quiere filtrar la consulta.
     *        Puede ser null.
     *
     * @return Si la ejecuci�n ha sido correcta, devuelve el resultado de la consulta.
     *         Si ocurre alg�n error, lanzar� una excepci�n.
     */
    public List<ListadoHistoricoAlumno> consultarHistoricoAlumno(String dni_alumno,
                                                                 String convocatoria,
                                                                 String curso) {
        try {
            int curso_aux;
            if (this.estaVacio(curso)) {
                curso_aux = -1;
            } else {
                curso_aux =  Integer.parseInt(curso);
            }
            ControladorAlumno DAOAlumno = new ControladorAlumno();
            return DAOAlumno.realizarConsultaHistoricoAlumno(dni_alumno, convocatoria, curso_aux);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Aseg�rese de introducir un valor num�rico en Curso");
        }
    } // fin del m�todo consultarHistoricoAlumno


    /** M�todo que obtiene la informaci�n de un alumno con un dni espec�fico.
     *  Si ocurre alg�n error o no encuentra ning�n alumno, lanzar� una excepci�n.
     *
     * @param dataAccessObject objeto con el que gestionamos el acceso a la
     *        Base de Datos.
     */
    public void obtenerDatosAlumno(DataAccessObject dataAccessObject) {
        ControladorAlumno DAOAlumno = new ControladorAlumno();
        this.nombre = DAOAlumno.obtenerNombreAlumno(dni,dataAccessObject);
        this.apellidos = DAOAlumno.obtenerApellidosAlumno(dni,dataAccessObject);
        this.n_mat = DAOAlumno.obtenerNumMatriculaAlumno(dni,dataAccessObject);
    } // fin del m�todo obtenerDatosAlumno


    /** M�todo que obtiene la informaci�n de un alumno con un dni espec�fico.
     *  Si ocurre alg�n error o no encuentra ning�n alumno, lanzar� una excepci�n.
     */
     public void obtenerDatosAlumno() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            ControladorAlumno DAOAlumno = new ControladorAlumno();
            this.nombre = DAOAlumno.obtenerNombreAlumno(dni,dataAccessObject);
            this.apellidos = DAOAlumno.obtenerApellidosAlumno(dni,dataAccessObject);
            this.n_mat = DAOAlumno.obtenerNumMatriculaAlumno(dni,dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
             dataAccessObject.rollback();
             throw new RuntimeException(e.getMessage());
        }
     }// fin del m�todo obtenerDatosAlumno

     
    /** M�todo que comprueba si una cadena est� vac�a. Se considerar� vac�a
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si est� vac�a.
     *
     * @return TRUE si la cadena est� vac�a. FALSE en caso contrario.
     */
    private boolean estaVacio(String cadena) {
	return ((cadena == null) || ("".equals(cadena)));
    } // fin del m�todo estaVacio


    /** M�todo que devuelve el dni del alumno.
     *
     * @return dni del alumno.
     */
    public String getDNI() {
        return this.dni;
    } // fin del m�todo getDNI


    /** M�todo que devuelve el n� de matr�cula del alumno.
     *
     * @return n� de matr�cula del alumno.
     */
    public String getN_Mat() {
        return this.n_mat;
    } // fin del m�todo getN_Mat


    /** M�todo que devuelve el nombre del alumno.
     *
     * @return nombre del alumno.
     */
    public String getNombre() {
        return this.nombre;
    } // fin dle m�todo getNombre


    /** M�todo que devuelve los apellidos del alumno.
     *
     * @return apellidos del alumno.
     */
    public String getApellidos() {
        return this.apellidos;
    } // fin del m�todo getApellidos

} // fin de la clase Alumno