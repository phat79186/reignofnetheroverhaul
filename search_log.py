import json
import sys

# Force UTF-8 stdout
sys.stdout.reconfigure(encoding='utf-8')

path = r"C:\Users\User\.gemini\antigravity\brain\f5318c8b-a8a5-473e-9de2-a58d1719ca45\.system_generated\logs\transcript.jsonl"
with open(path, "r", encoding="utf-8") as f:
    for line in f:
        try:
            obj = json.loads(line)
            # Find tool calls containing CommandLine
            tool_calls = obj.get("tool_calls", [])
            for tc in tool_calls:
                args = tc.get("args", {})
                if "CommandLine" in args:
                    print("CMD in step", obj.get("step_index"), ":", args["CommandLine"])
        except Exception as e:
            pass
