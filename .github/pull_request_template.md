## Description

Please include a summary of the change and which issue(s) have been fixed. Please also include relevant motivation and context. List any dependencies that are required for this change.

Fixes # (issue)

## Type of change

- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] This change requires a documentation update
- [ ] This change is a documentation update ([1.](#reference-markers) only applicable if no code changed except for Javadoc comments)
- [ ] This change is a translation file update ([2.](#reference-markers) only applicable if no code changed except for translation necessary changes)

## How Has This Been Tested?

Please describe the tests that you ran to verify your changes. Provide instructions, so we can reproduce. Please also list any relevant details for your test configuration.

- [ ] Test A
- [ ] Test B

**Test Configuration**:
- OS: [e.g. OSX Catalina 10.15.6]
- Minecraft Version [e.g. 1.16.2]
- Java Version: [e.g. OpenJDK 64-Bit Server VM GraalVM CE 20.1.0 (build 11.0.7+10-jvmci-20.1-b02, mixed mode, sharing)]
- API: true/false (Is this pull request about the API)

## Checklist:

- [ ] My code follows the style guidelines of this project (To Be Determined. Try to Follow Bouncer Pattern For Now)
- [ ] My code keeps bracketless single If/Else statements where reasonable
- [ ] My code uses annotations where reasonable ([3.](#reference-markers) e.g. @NotNull where returns are never null and in method arguments where null is unexpected)
- [ ] I have performed a self-review of my own code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] If this is a translation update, check this box to signal that you would not/could not use Crowdin ([4.](#reference-markers) e.g. if you needed to update the code for a translation to work properly)

## Reference Markers:

1. The Github Pages /docs/ does not count as code for this purpose, so it falls under here.
2. Translation necessary changes refers to parameters in code such as TranslatableText.
Basically, the parameters in code would not work with the translation for that locale without changes to the code.
Please update the English/US translation files if applicable. Crowdin's setup to import the English/US files for translation.
3. Use Jetbrain's annotations over JavaX. The PR will be modified/denied unless the annotation cannot be performed with Jetbrains (and can be performed with JavaX).
4. If this is just a simple translation which doesn't modify code, then submit the translation through [Crowdin][crowdin-link] instead.

[crowdin-link]: <https://crwd.in/randomtech> "Crowdin Link"