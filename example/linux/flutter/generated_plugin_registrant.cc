//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <beauty_cam/beauty_cam_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) beauty_cam_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "BeautyCamPlugin");
  beauty_cam_plugin_register_with_registrar(beauty_cam_registrar);
}
