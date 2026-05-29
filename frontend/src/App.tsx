import {AuthProvider} from "./features/auth/context/AuthContext.tsx";
import AppRouter from "./app/router/Router.tsx";

function App() {
    return (
        <AuthProvider>
            <AppRouter/>
        </AuthProvider>
    )
}

export default App;