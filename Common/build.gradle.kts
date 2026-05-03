plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
}

dependencies {
    modCompileOnlyApi(sharedLibs.puzzleslib.common)
}

multiloader {
    mixins {
        clientMixin(
            "EquipmentLayerRendererMixin",
            "FoilTypeMixin",
            "ItemFeatureRendererMixin",
            "ModelFeatureRendererMixin",
            "ModelWrapperMixin",
            "ShieldSpecialRendererMixin",
            "ThrownTridentRendererMixin"
        )
    }
}
