import Navbar from "./components/common/Navbar/Navbar.tsx";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home/Home.tsx";
import Rooms from "./pages/Rooms/Rooms.tsx";
import Booking from "./pages/Booking/Booking.tsx";
import SignIn from "./pages/SignIn/SignIn.tsx";

function App() {
    return (
        <>
            <Navbar/>

            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/rooms" element={<Rooms/>}/>
                <Route path="/booking" element={<Booking/>}/>
                <Route path="/signin" element={<SignIn/>}/>
            </Routes>
        </>
    )
}

export default App;