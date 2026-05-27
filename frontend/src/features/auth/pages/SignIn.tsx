import styles from "./SignIn.module.css"
import {Link, useNavigate} from "react-router-dom";
import {type ChangeEvent, type SyntheticEvent, useState} from "react";
import type {LoginRequest} from "../types/LoginRequest.ts";
import authService from "../api/authService.ts";
import {useAuth} from "../context/AuthContext.tsx";
import type {RegisterRequest} from "../types/RegisterRequest.ts";


const SignIn = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    const [form, setForm] = useState<LoginRequest>({
        email: "",
        password: ""
    })

    const [touched, setTouched] = useState<Record<string, boolean>>({})
    const [globalError, setGlobalError] = useState<string | null>(null);
    const [fieldErrors, setFieldErrors] = useState<Partial<Record<keyof LoginRequest, string
    >>>({})
    const [loading, setLoading] = useState(false);

    const validateFields = () => {
        const errors: Partial<Record<keyof LoginRequest, string>> = {};

        if (!form.email.trim()) {
            errors.email = "Email is required";
        } else if (!/\S+@\S+\.\S+/.test(form.email)) {
            errors.email = "Please enter a valid email address";
        }

        if (!form.password.trim()) {
            errors.password = "Password is requiered";
        } else if (form.password.length < 8) {
            errors.password = "Password must be at least 8 characters";
        }

        return errors;

    }

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        const fieldName = name as keyof RegisterRequest;
        setForm(prev => ({...prev, [fieldName]: value}))

        // Limpiar error ante cambio del usuario en el input, asi no siempre estar mostrando el error molesto.
        setFieldErrors(prev => ({
            ...prev,
            [fieldName]: undefined,
        }))
        // el error solo ocurrira si se manda el formulario con campos invalidos
    }

    const handleBlur = (e: ChangeEvent<HTMLInputElement>) => {
        setTouched(prev => ({...prev, [e.target.name]: true}));
    }

    // const isFieldValid = (field: keyof LoginRequest) => {
    //     switch (field) {
    //         case "email":
    //             return /\S+@\S+\.\S+/.test(form.email);
    //         case "password":
    //             return form.password.length >= 8
    //         default:
    //             return true;
    //     }
    // }
    //
    // const validate = (): string | null => {
    //     if (!form.email.trim()) return "Email is required";
    //     if (!form.password.trim()) return "Password is required";
    //     return null;
    // }

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();

        const validationError = validateFields();
        if (Object.keys(validationError).length > 0) {
            setFieldErrors(validationError)
            setGlobalError(null) // -> no global error
            return;
        }

        setFieldErrors({});
        setLoading(true);
        try {
            const response = await authService.login(form);
            login(response.token);
            setGlobalError(null);
            navigate("/");
        } catch (err) {
            if (err instanceof Error) {
                setGlobalError(err.message)
            } else {
                setGlobalError("Login failed. Please try again.")
            }
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className={styles.container}>
            <form noValidate className={styles.form} method={"POST"} onSubmit={handleSubmit}>
                <div className={styles.card}>

                    <p className={styles.eyebrow}>Welcome back again!</p>

                    <div className={styles.ornament}>
                        <div className={styles.ornamentLine}/>
                        <div className={styles.ornamentDiamond}/>
                        <div className={styles.ornamentLine}/>
                    </div>

                    <h1 className={styles.brand}>Grand Palacio</h1>
                    <p className={styles.sub}>Hotel &amp; Reservations</p>

                    {globalError && (
                        <p className={styles.error}>{globalError}</p>
                    )}


                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Email</label>
                        <div className={styles.inputWrap}>
                            <input
                                type="email"
                                name={"email"}
                                value={form.email}
                                className={`${styles.input} ${fieldErrors?.email ? styles.inputError : ""}`}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                placeholder={"email@domain.com"}/>
                        </div>
                        {touched.email && fieldErrors.email && (
                            <p className={styles.fieldError}>
                                {fieldErrors?.email}
                            </p>
                        )}

                    </div>

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Password</label>
                        <div className={styles.inputWrap}>
                            <input
                                type="password"
                                name={"password"}
                                value={form.password}
                                className={`${styles.input} ${fieldErrors?.password ? styles.inputError : ""}`}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                placeholder={"*************"}/>

                        </div>
                        {/*Not render a simbple <p> even if there are not a error*/}
                        {touched.password && fieldErrors.password && (
                            <p className={styles.fieldError}>
                                {fieldErrors?.password}
                            </p>
                        )}
                    </div>

                    <div className={styles.forgot}>
                        <a href="#">Forget password?</a>
                    </div>

                    <button
                        type={"submit"}
                        className={styles.btn}
                    >{loading ? "Signing in..." : "Sign in"}</button>

                    <div className={styles.divider}>
                        <span>o</span>
                    </div>

                    <div className={styles.footer}>
                        <span>Don't have an account?</span>
                        <Link to="/register">Signup here</Link>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default SignIn;