import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home/Home.tsx";
import SignIn from "./features/auth/pages/SignIn.tsx";
import RoomsPreview from "./features/rooms/pages/RoomsPreview.tsx";
import Booking from "./features/reservations/pages/Booking.tsx";
import MainLayout from "./shared/components/layuot/MainLayout.tsx";
import SignUp from "./features/auth/pages/SignUp.tsx";
import {AuthProvider} from "./features/auth/context/AuthContext.tsx";

function App() {
    return (
        <AuthProvider>
            <Routes>
                {/* Rutas con navbar */}
                <Route element={<MainLayout/>}>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/rooms" element={<RoomsPreview/>}/>
                    <Route path="/booking" element={<Booking/>}/>
                </Route>

                {/* Rutas sin navbar */}
                <Route path={"/login"} element={<SignIn/>}/>
                <Route path={"/register"} element={<SignUp/>}/>
            </Routes>
        </AuthProvider>
    )
}

export default App;