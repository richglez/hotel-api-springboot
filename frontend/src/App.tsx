import {AuthProvider} from "./features/auth/context/AuthProvider.tsx"
import AppRouter from "./app/router/Router.tsx";

function App() {
    return (
        <AuthProvider>
            <AppRouter/>
        </AuthProvider>
    )
}

export default App;