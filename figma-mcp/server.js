import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { CallToolRequestSchema, ListToolsRequestSchema } from "@modelcontextprotocol/sdk/types.js";
import fetch from "node-fetch";

const server = new Server(
  {
    name: "figma-mcp",
    version: "0.1.0",
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

// List available tools
server.setRequestHandler(ListToolsRequestSchema, async () => {
  return {
    tools: [
      {
        name: "getFigmaDesign",
        description: "Fetch a Figma design JSON by file key",
        inputSchema: {
          type: "object",
          properties: {
            fileKey: { type: "string" },
          },
          required: ["fileKey"],
        },
      },
    ],
  };
});

// Handle tool calls
server.setRequestHandler(CallToolRequestSchema, async (request) => {
  const { name, arguments: args } = request.params;
  
  if (name === "getFigmaDesign") {
    const { fileKey } = args;

    const res = await fetch(`https://api.figma.com/v1/files/${fileKey}`, {
      headers: {
        "X-Figma-Token": process.env.FIGMA_TOKEN,
      },
    });

    const data = await res.json();
    return {
      content: [{ type: "text", text: JSON.stringify(data, null, 2) }],
    };
  }
  throw new Error(`Unknown tool: ${name}`);
});

// Start the server using stdio transport
const transport = new StdioServerTransport();
await server.connect(transport);
