import {Route, Routes} from "react-router-dom";
import Home from "../../pages/Home/Home.tsx";
import SignIn from "../../features/auth/pages/SignIn.tsx";
import SignUp from "../../features/auth/pages/SignUp.tsx";
import RoomsPreview from "../../features/rooms/pages/RoomsPreview.tsx";
import Booking from "../../features/reservations/pages/Booking.tsx";
import MainLayout from "../../shared/components/layuot/MainLayout.tsx";
import PrivateRoute from "../../features/auth/components/PrivateRoute.tsx";

const AppRouter = () => {
    return (
        <Routes>
            {/* Rutas con navbar */}
            <Route element={<MainLayout/>}>
                <Route path="/" element={<Home/>}/>
                <Route path="/rooms" element={<RoomsPreview/>}/>
                <Route path="/booking" element={
                    <PrivateRoute>
                        <Booking/>
                    </PrivateRoute>
                }/>
            </Route>

            {/* Rutas sin navbar */}
            <Route path="/login" element={<SignIn/>}/>
            <Route path="/register" element={<SignUp/>}/>
        </Routes>
    );
};

export default AppRouter;