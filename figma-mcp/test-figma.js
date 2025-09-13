import fetch from "node-fetch";

const fileKey = "YKMuYOIGjBKp4uyzNxZKHp";
const token = process.env.FIGMA_TOKEN;

console.log("Fetching Figma design data...");
console.log("File Key:", fileKey);
console.log("Token (first 10 chars):", token ? token.substring(0, 10) + "..." : "NOT SET");

try {
  const response = await fetch(`https://api.figma.com/v1/files/${fileKey}`, {
    headers: {
      "X-Figma-Token": token,
    },
  });

  if (!response.ok) {
    console.error("HTTP Error:", response.status, response.statusText);
    const errorText = await response.text();
    console.error("Error details:", errorText);
  } else {
    const data = await response.json();
    console.log("Success! Figma data received:");
    console.log("Document name:", data.name);
    console.log("Last modified:", data.lastModified);
    console.log("Number of pages:", data.document?.children?.length || 0);
    
    // Show first few nodes as a sample
    if (data.document?.children?.length > 0) {
      const firstPage = data.document.children[0];
      console.log("First page name:", firstPage.name);
      console.log("First page children count:", firstPage.children?.length || 0);
    }
  }
} catch (error) {
  console.error("Error fetching Figma data:", error.message);
}