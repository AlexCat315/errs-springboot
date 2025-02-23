use get_if_addrs::get_if_addrs;

#[tauri::command]
pub fn get_one_address() -> Result<String, String> {
    let interfaces = get_if_addrs().map_err(|e| e.to_string())?;

    interfaces
        .iter()
        .find(|iface| {
            // Check if not loopback and if it's a physical interface.
            !iface.is_loopback() && is_physical_interface(&iface.name)
        })
        .map(|iface| iface.addr.ip().to_string())
        .ok_or_else(|| "No physical IP address found".into())
}

// 物理接口识别逻辑
fn is_physical_interface(name: &str) -> bool {
    !name.starts_with("docker")
        && !name.starts_with("br-")
        && !name.starts_with("veth")
        && !name.starts_with("lo")
}
