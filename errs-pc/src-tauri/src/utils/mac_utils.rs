use pnet::datalink::{self, NetworkInterface};
use tauri::command;

#[command]
pub fn get_mac_address() -> Option<String> {
    // 获取系统中所有可用的网络接口列表
    let interfaces = datalink::interfaces();

    // 定义一个闭包函数，用于判断一个网络接口是否是 "有效" 的，
    // 我们会用这个闭包来过滤接口列表。
    let is_valid_interface = |iface: &&NetworkInterface| {
        // 排除回环接口 (loopback), 回环接口通常用于本地测试
        if iface.is_loopback() {
            return false;
        }
        // 排除 Docker 相关的虚拟接口，通常名称以 "docker" 开头
        if iface.name.starts_with("docker") {
            return false;
        }
        // 排除 veth 接口，这通常是 Linux 容器使用的虚拟以太网接口
        if iface.name.starts_with("veth") {
            return false;
        }
        // 排除网桥接口，名称可能以 "br-" 开头
        if iface.name.starts_with("br-") {
            return false;
        }
        // 排除 VMware 相关的虚拟网络接口，名称可能以 "vmnet" 开头
        if iface.name.starts_with("vmnet") {
            return false;
        }
        // 排除 MAC 地址为全零的接口，这通常表示接口未配置或无效
        if let Some(mac_addr) = iface.mac {
            if mac_addr.to_string().eq("00:00:00:00:00:00") {
                return false;
            }
        } else {
            return false; // No MAC address present, consider invalid
        }
        // 如果以上条件都不满足，则认为该接口是有效的
        true
    };

    // 使用过滤器闭包 `is_valid_interface` 从所有接口中筛选出有效的接口。
    // 收集到一个 Vec 集合中，方便后续排序。
    let mut sorted_interfaces: Vec<&NetworkInterface> = interfaces.iter()
        .filter(is_valid_interface)
        .collect();

    // 定义一个闭包函数用于给接口赋予优先级，用于排序。
    let interface_priority = |iface: &NetworkInterface| {
        // 基于接口的名称和状态赋予不同的优先级数值
        match (iface.is_up(), iface.ips.len()) {
            // 优先级最高：名称以 "en" 开头的已启用接口 (通常是有线以太网接口) 并且有IP地址
            (true, _) if iface.name.starts_with("en") => 0,
            // 其次：名称以 "wl" 开头的已启用接口 (通常是无线网卡) 并且有IP地址
            (true, _) if iface.name.starts_with("wl") => 1,
            // 再次：其他已启用的接口并且有IP地址
            (true, _) => 2,
            // 优先级最低：未启用的接口
            _ => 3,
        }
    };

    // 根据接口的优先级进行排序。优先级数值越小，排序越靠前。
    sorted_interfaces.sort_by(|a, b| {
        interface_priority(a).cmp(&interface_priority(b))
    });

    // 从排序后的有效接口列表中，选取第一个接口（优先级最高的接口）。
    // 使用 `and_then` 链式操作，如果 `first()` 返回 `Some`, 则继续处理，否则返回 `None`。
    sorted_interfaces.first().and_then(|iface| {
        // 获取该接口的 MAC 地址
        if let Some(mac) = iface.mac {
            // 将 MAC 地址格式化为字符串形式 (例如 "00:11:22:33:44:55")
            Some(format!("{}", mac))
        } else {
            None // Interface has no MAC address
        }
    })
    // 如果没有找到有效的网络接口，最终 `and_then` 或者 `first()` 返回 `None`,
    // 函数 `get_mac_address` 也会返回 `Option::None`, 表示未能获取到 MAC 地址。
}