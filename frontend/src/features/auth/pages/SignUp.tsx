import {useState} from "react";
import styles from "./SignUp.module.css";
import {Link, useNavigate} from "react-router-dom";
import authService from "../api/authService";
import type {RegisterRequest} from "../types/RegisterRequest";
import type {ChangeEvent, SyntheticEvent} from "react";

const SignUp = () => {
    const navigate = useNavigate();

    const [form, setForm] = useState<RegisterRequest>({
        name: "",
        lastName: "",
        email: "",
        password: "",
        phone: "",
    });

    const [touched, setTouched] = useState<Record<string, boolean>>({})
    const [globalError, setGlobalError] = useState<string | null>(null);
    const [fieldErrors, setFieldError] = useState<Partial<Record<keyof RegisterRequest, string
    >>>({})
    const [loading, setLoading] = useState(false); // inicialmente no se esta mandando nada -> false

    const validateFields = () => {
        const errors: Partial<Record<keyof RegisterRequest, string>> = {};

        if (!form.email.trim()) {
            errors.email = "Email is required"
        } else if (!/\S+@\S+\.\S+/.test(form.email)) {
            errors.email = "Please enter a valid email address";
        }

        if (!form.name.trim()) {
            errors.name = "Name is required";
        }

        if (!form.lastName.trim()) {
            errors.lastName = "Last name is required";
        }

        if (!form.password.trim()) {
            errors.password = "Password is required";
        } else if (form.password.length < 8) {
            errors.password = "Password must be at least 8 characters";
        }

        return errors
    }

    const handleBlur = (e: ChangeEvent<HTMLInputElement>) => {
        setTouched(prev => ({...prev, [e.target.name]: true}))
    }


    const isFieldValid =
        (field: keyof RegisterRequest): boolean => {
            switch (field) {
                case "email":
                    return /\S+@\S+\.\S+/.test(form.email);
                case "name":
                    return form.name.trim().length > 0;
                case "lastName":
                    return form.lastName.trim().length > 0;
                case "password":
                    return form.password.length >= 8;
                default:
                    return true;
            }
        }

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target
        const fieldName = name as keyof RegisterRequest;

        setForm(prev => ({...prev, [fieldName]: value}));

        // Ante cambios en el input (es decir cuando el usuario esta escribiendo sobre el input) -> marcar indefinido el error, osea que puede haber un error, pero no mostrarlo en pantalla cuando el usuario esta escribiendo.
        setFieldError(prev => ({
            ...prev,
            [fieldName]: undefined
        }))
        // aqui el error solo ocurre al mandar el formulario con campos invalidos
    };

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {

        e.preventDefault();  // 1. evita reload del navegador

        const validationField = validateFields()

        if (Object.keys(validationField).length > 0) {
            setFieldError(validationField);
            setGlobalError(null); // no hay un error global nadamas de campo
            return; // termina handleSubmit
        }

        // Al corregir los errores -> limpialos
        setGlobalError(null); // si pasa la prueba de arriba -> no hay un error global
        setFieldError({}) // -> no hay error por campos
        setLoading(true); // pasara a un loading -> send
        try {
            const response = await authService.register(form); // 3. llama al backend
            localStorage.setItem("token", response.token); // 4. guarda el token
            navigate("/"); // 5. redirige a HOME
        } catch (err) {
            if (err instanceof Error) {
                setGlobalError(err.message) // 6a. error conocido
            } else {
                setGlobalError("Registration failed") // 6b. error desconocido
            }
        } finally {
            setLoading(false); // termina la carga si hubo un exito o un error
        }
    };

    return (
        <div className={styles.container}>
            <form noValidate method={"POST"} onSubmit={handleSubmit}>
                <div className={styles.card}>

                    <p className={styles.eyebrow}>Create your account</p>

                    <div className={styles.ornament}>
                        <div className={styles.ornamentLine}/>
                        <div className={styles.ornamentDiamond}/>
                        <div className={styles.ornamentLine}/>
                    </div>

                    <h1 className={styles.brand}>Grand Palacio</h1>
                    <p className={styles.sub}>Hotel &amp; Reservations</p>

                    {globalError && <p className={styles.error}>{globalError}</p>}

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Email</label>
                        <div className={styles.inputWrap}>
                            <input
                                type="email"
                                name="email"
                                className={`${styles.input} ${fieldErrors?.email ? styles.inputError : ""}`}
                                placeholder="email@domain.com"
                                value={form.email}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.email && isFieldValid("email") && (
                                <span className={styles.checkIcon}>
                                     <svg viewBox="0 0 20 20" fill="none">
                                            <circle cx="10" cy="10" r="9" stroke="currentColor" strokeWidth="1.5"/>
                                            <path d="M6 10l3 3 5-5" stroke="currentColor" strokeWidth="1.5"
                                                  strokeLinecap="round"
                                                  strokeLinejoin="round"/>
                                     </svg>
                                  </span>
                            )}
                        </div>
                        {touched.email && fieldErrors.email && (
                            <p className={styles.fieldError}>{fieldErrors?.email}</p>
                        )}
                    </div>

                    <div className={styles.row}>
                        <div className={styles.fieldWrap}>
                            <label className={styles.label}>Name</label>
                            <div className={styles.inputWrap}>
                                <input
                                    type="text"
                                    name="name"
                                    className={`${styles.input} ${fieldErrors?.name ? styles.inputError : ""}`}
                                    placeholder="John"
                                    value={form.name}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {touched.name && isFieldValid("name") && (
                                    <span className={styles.checkIcon}>
                                        <svg viewBox="0 0 20 20" fill="none">
                                            <circle cx="10" cy="10" r="9" stroke="currentColor" strokeWidth="1.5"/>
                                            <path d="M6 10l3 3 5-5" stroke="currentColor" strokeWidth="1.5"
                                                  strokeLinecap="round"
                                                  strokeLinejoin="round"/>
                                        </svg>
                                    </span>
                                )}
                            </div>
                            {touched.name && fieldErrors.name && (
                                <p className={styles.fieldError}>{fieldErrors?.name}</p>
                            )}
                        </div>

                        <div className={styles.fieldWrap}>
                            <label className={styles.label}>Last name</label>
                            <div className={styles.inputWrap}>
                                <input
                                    type="text"
                                    name="lastName"
                                    className={`${styles.input} ${fieldErrors?.lastName ? styles.inputError : ""}`}
                                    placeholder="Doe"
                                    value={form.lastName}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {touched.lastName && isFieldValid("lastName") && (
                                    <span className={styles.checkIcon}>
                                        <svg viewBox="0 0 20 20" fill="none">
                                           <circle cx="10" cy="10" r="9" stroke="currentColor" strokeWidth="1.5"/>
                                         <path d="M6 10l3 3 5-5" stroke="currentColor" strokeWidth="1.5"
                                               strokeLinecap="round"
                                               strokeLinejoin="round"/>
                                       </svg>
                                  </span>
                                )}
                            </div>
                            {touched.lastName && fieldErrors.lastName && (
                                <p className={styles.fieldError}>{fieldErrors?.lastName}</p>
                            )}
                        </div>
                    </div>

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Password</label>
                        <div className={styles.inputWrap}>
                            <input
                                minLength={8}
                                type="password"
                                name="password"
                                className={`${styles.input} ${fieldErrors?.password ? styles.inputError : ""}`}
                                placeholder="••••••••••••"
                                value={form.password}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.password && isFieldValid("password") && (
                                <span className={styles.checkIcon}>
                                        <svg viewBox="0 0 20 20" fill="none">
                                            <circle cx="10" cy="10" r="9" stroke="currentColor" strokeWidth="1.5"/>
                                            <path d="M6 10l3 3 5-5" stroke="currentColor" strokeWidth="1.5"
                                                  strokeLinecap="round"
                                                  strokeLinejoin="round"/>
                                        </svg>
                                    </span>
                            )}
                        </div>
                        {/*Not render a simbple <p> even if there are not a error*/}
                        <p className={styles.hint}>Minimum 8 characters</p>
                        {touched.password && fieldErrors.password && (
                            <p className={styles.fieldError}>{fieldErrors?.password}</p>
                        )}
                    </div>

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Phone</label>
                        <input
                            type="tel"
                            name="phone"
                            className={styles.input}
                            placeholder="+555 0000 000"
                            value={form.phone}
                            onChange={handleChange}
                        />
                    </div>

                    <button
                        type={"submit"}
                        className={styles.btn}
                        disabled={loading}
                    >
                        {loading ? "Creating account..." : "Create account"}
                    </button>

                    <div className={styles.divider}>
                        <span>o</span>
                    </div>

                    <div className={styles.footer}>
                        <span>Already have an account?</span>
                        <Link to="/login">Sign in</Link>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default SignUp;