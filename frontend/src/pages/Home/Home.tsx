import HeroSection from "./sections/HeroSection.tsx";
import RoomsPreview from "./sections/RoomsPreview.tsx";
import AmenitiesSection from "./sections/AmenitiesSection.tsx";
import CtaSection from "./sections/CtaSection.tsx";


const Home = () => {
    return (
        // Home Orquesta a todas las paginas
        <main>
            <HeroSection />
            <RoomsPreview />
            <AmenitiesSection />
            <CtaSection />
        </main>
    )
}

export default Home;