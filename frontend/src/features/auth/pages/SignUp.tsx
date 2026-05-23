import styles from "./SignUp.module.css"
import {Link, useNavigate} from "react-router-dom";
import authService from "../api/authService.ts";
import {useState} from "react";
import type {RegisterRequest} from "../types/RegisterRequest.ts";

const SignUp = () => {
    const navigate = useNavigate()

    const [form, setForm] = useState<RegisterRequest>({
        name: "",
        lastName: "",
        email: "",
        password: "",
        phone: ""
    })

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm(prev => ({...prev, [e.target.name]: e.target.value}));
    }

    const handleSubmit = async () => {
        setError(null); // Limpiar errores previos
        setLoading(true) // esta cargando
        try {
            const response = await authService.register(form);
            localStorage.setItem("token", response.token) // esto permite que el navegador recuerde que el usuario ya inicio sesion incluso si refresca la pagina
            navigate("/") // envia a home si todo fue bien
        } catch (err) {
            setError("Registration failed. Please try again.");
        } finally {
            setLoading(false); // termina la carga
        }
    }

    return (
        <div className={styles.container}>
            <div className={styles.card}>

                <p className={styles.eyebrow}>Create your account</p>

                <div className={styles.ornament}>
                    <div className={styles.ornamentLine}/>
                    <div className={styles.ornamentDiamond}/>
                    <div className={styles.ornamentLine}/>
                </div>

                <h1 className={styles.brand}>Grand Palacio</h1>
                <p className={styles.sub}>Hotel &amp; Reservations</p>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Email</label>
                    <input
                        type="email"
                        name="email"
                        className={styles.input}
                        placeholder="email@domain.com"
                        value={form.email}
                        onChange={handleChange}
                    />
                </div>

                {/* Name y LastName en grid de dos columnas */}
                <div className={styles.row}>
                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Name</label>
                        <input type="text" className={styles.input} placeholder="John"/>
                    </div>
                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Last name</label>
                        <input type="text" className={styles.input} placeholder="Doe"/>
                    </div>
                </div>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Password</label>
                    <input type="password" className={styles.input} placeholder="••••••••••••"/>
                    <p className={styles.hint}>Minimum 8 characters</p>
                </div>

                <div className={styles.fieldWrap}>
                    <label className={styles.label}>Phone</label>
                    <input type="tel" className={styles.input} placeholder="+555 0000 000"/>
                </div>

                <button className={styles.btn}>Create account</button>

                <div className={styles.divider}>
                    <span>o</span>
                </div>

                <div className={styles.footer}>
                    <span>Already have an account?</span>
                    <Link to="/login">Sign in</Link>
                </div>
            </div>
        </div>
    )
}

export default SignUp