/*
 * The MIT License (MIT)
 *
 * Copyright (c) Despector <https://despector.voxelgenesis.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.despector.config;

import com.google.common.collect.Lists;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.despector.emitter.format.EmitterFormat.BracePosition;
import org.spongepowered.despector.emitter.format.EmitterFormat.WrappingStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * The global configuration.
 */
public class ConfigBase {

    @Setting(comment = "Emitter configuration")
    public EmitterConfig emitter = new EmitterConfig();
    @Setting(comment = "Cleanup configuration")
    public CleanupConfig cleanup = new CleanupConfig();
    @Setting(comment = "Kotlin specific configuration")
    public KotlinConfig kotlin = new KotlinConfig();
    @Setting(comment = "Formatting configuration, a defined formatter config from the command line will override these settings.")
    public FormatterConfig formatter = new FormatterConfig();

    @Setting(comment = "Targeted cleanup operations")
    public List<CleanupConfigSection> cleanup_sections = new ArrayList<>();

    @Setting(value = "print-opcodes-on-error", comment = "Prints out opcodes of a method when it fails to decompile.")
    public boolean print_opcodes_on_error = Boolean.valueOf(System.getProperty("despector.debug.printerrors", "false"));

    /**
     * Configuration for the emitter settings.
     */
    @ConfigSerializable
    public static class EmitterConfig {

        @Setting(value = "formatting-path", comment = "The path of the formatter configuration")
        public String formatting_path = "eclipse_formatter.xml";
        @Setting(value = "import-order-path", comment = "The path of the import order configuration")
        public String imports_path = "eclipse.importorder";
        @Setting(value = "formatting-type", comment = "One of: eclipse,intellij")
        public String formatting_type = "eclipse";
        @Setting(value = "emit-synthetics", comment = "Whether to emit synthetic members")
        public boolean emit_synthetics = false;

        public boolean emit_this_for_fields = true;
        public boolean emit_this_for_methods = false;

    }

    /**
     * Configuration for the cleanup operations.
     */
    @ConfigSerializable
    public static class CleanupConfig {

        @Setting(value = "operations", comment = "Cleanup operations to apply before emitting")
        public List<String> operations = new ArrayList<>();

    }

    /**
     * Configuration for a targeted cleanup operation.
     */
    @ConfigSerializable
    public static class CleanupConfigSection {

        @Setting(value = "operations", comment = "Cleanup operations to apply before emitting")
        public List<String> operations = new ArrayList<>();

        @Setting(value = "targets", comment = "Class targets to apply this cleanup to")
        public List<String> targets = new ArrayList<>();

    }

    /**
     * Configuration specific to kotlin decompilation.
     */
    @ConfigSerializable
    public static class KotlinConfig {

        @Setting(value = "replace-multiline-strings", comment = "Whether to replace strings containing new lines with raw strings")
        public boolean replace_mulit_line_strings = true;

    }

    /**
     * Configuration for the output formatting.
     */
    @ConfigSerializable
    public static class FormatterConfig {

        public General general = new General();
        public Imports imports = new Imports();
        public Classes classes = new Classes();
        public Enums enums = new Enums();
        public Fields fields = new Fields();
        public Methods methods = new Methods();
        public Misc misc = new Misc();
        public Arrays arrays = new Arrays();
        public Switches switches = new Switches();
        public Loops loops = new Loops();
        public Ifs ifs = new Ifs();
        public TryCatches trycatches = new TryCatches();
        public Annotations annotations = new Annotations();

        /**
         * Configuration for general formatting.
         */
        @ConfigSerializable
        public static class General {

            public int line_split = 999;
            public int indentation_size = 4;
            public int continuation_indentation = 2;
            public boolean indent_with_spaces = true;
            public boolean indent_empty_lines = false;
            public boolean insert_new_line_at_end_of_file_if_missing = true;

        }

        /**
         * Configuration for import formating.
         */
        @ConfigSerializable
        public static class Imports {

            public List<String> import_order = Lists.newArrayList("/#", "", "java", "javax");

            public int blank_lines_before_package = 0;
            public int blank_lines_after_package = 1;
            public int blank_lines_before_imports = 1;
            public int blank_lines_after_imports = 1;
            public int blank_lines_between_import_groups = 1;

        }

        /**
         * Configuration for class formatting.
         */
        @ConfigSerializable
        public static class Classes {

            public boolean insert_space_before_comma_in_superinterfaces = false;
            public boolean insert_space_after_comma_in_superinterfaces = true;

            public int blank_lines_before_first_class_body_declaration = 1;
            public BracePosition brace_position_for_type_declaration = BracePosition.SAME_LINE;
            public WrappingStyle alignment_for_superclass_in_type_declaration = WrappingStyle.DO_NOT_WRAP;
            public WrappingStyle alignment_for_superinterfaces_in_type_declaration = WrappingStyle.WRAP_WHEN_NEEDED;
        }

        /**
         * Configuration for enum formatting.
         */
        @ConfigSerializable
        public static class Enums {

            public boolean insert_space_before_opening_brace_in_enum_declaration = true;
            public boolean insert_space_before_comma_in_enum_constant_arguments = false;
            public boolean insert_space_after_comma_in_enum_constant_arguments = true;
            public boolean insert_space_before_comma_in_enum_declarations = false;
            public boolean insert_space_after_comma_in_enum_declarations = true;
            public boolean insert_space_before_opening_paren_in_enum_constant = false;
            public boolean insert_space_after_opening_paren_in_enum_constant = false;
            public boolean insert_space_before_closing_paren_in_enum_constant = false;
            public boolean indent_body_declarations_compare_to_enum_declaration_header = true;
            public BracePosition brace_position_for_enum_declaration = BracePosition.SAME_LINE;
            public WrappingStyle alignment_for_superinterfaces_in_enum_declaration = WrappingStyle.WRAP_WHEN_NEEDED;
            public WrappingStyle alignment_for_enum_constants = WrappingStyle.WRAP_ALL;
        }

        /**
         * Configuration for field formatting.
         */
        @ConfigSerializable
        public static class Fields {

            public boolean align_type_members_on_columns = false;
        }

        /**
         * Configuration for method formatting.
         */
        @ConfigSerializable
        public static class Methods {

            public boolean insert_space_between_empty_parens_in_method_declaration = false;
            public boolean insert_space_before_comma_in_method_invocation_arguments = false;
            public boolean insert_space_after_comma_in_method_invocation_arguments = true;
            public boolean insert_new_line_in_empty_method_body = false;
            public boolean insert_space_before_comma_in_method_declaration_throws = false;
            public boolean insert_space_after_comma_in_method_declaration_throws = true;
            public boolean insert_space_before_comma_in_method_declaration_parameters = false;
            public boolean insert_space_after_comma_in_method_declaration_parameters = true;
            public boolean insert_space_before_opening_paren_in_method_declaration = false;
            public boolean insert_space_before_closing_paren_in_method_declaration = false;
            public boolean insert_space_before_opening_brace_in_method_declaration = true;
            public BracePosition brace_position_for_method_declaration = BracePosition.SAME_LINE;
            public WrappingStyle alignment_for_parameters_in_method_declaration = WrappingStyle.WRAP_WHEN_NEEDED;

        }

        /**
         * Configuration for general statement formatting.
         */
        @ConfigSerializable
        public static class Misc {

            public boolean new_lines_at_block_boundaries = true;
            public boolean insert_space_after_unary_operator = false;
            public boolean insert_space_before_binary_operator = true;
            public boolean insert_space_after_binary_operator = true;
            public boolean insert_space_after_lambda_arrow = true;
        }

        /**
         * Configuration for array formatting.
         */
        @ConfigSerializable
        public static class Arrays {

            public boolean insert_space_between_brackets_in_array_type_reference = false;
            public boolean insert_space_after_opening_brace_in_array_initializer = false;
            public boolean insert_new_line_before_closing_brace_in_array_initializer = false;
            public WrappingStyle alignment_for_expressions_in_array_initializer = WrappingStyle.WRAP_WHEN_NEEDED;
            public BracePosition brace_position_for_array_initializer = BracePosition.SAME_LINE;
            public boolean insert_space_after_opening_bracket_in_array_allocation_expression = false;
            public boolean insert_space_before_closing_bracket_in_array_allocation_expression = false;
            public boolean insert_space_before_comma_in_array_initializer = false;
            public boolean insert_space_after_comma_in_array_initializer = true;
            public boolean insert_space_before_opening_bracket_in_array_type_reference = false;
            public boolean insert_space_before_opening_bracket_in_array_reference = false;
            public boolean insert_space_before_closing_bracket_in_array_reference = false;
        }

        /**
         * Configuration for switch formatting.
         */
        @ConfigSerializable
        public static class Switches {

            public boolean insert_space_after_opening_paren_in_switch = false;
            public boolean insert_space_before_closing_paren_in_switch = false;
            public boolean insert_space_before_colon_in_case = false;
            public boolean insert_space_after_colon_in_case = false;
            public boolean insert_space_before_opening_brace_in_switch = true;
            public boolean indent_breaks_compare_to_cases = true;
            public boolean indent_switchstatements_compare_to_switch = false;
            public boolean indent_switchstatements_compare_to_cases = true;
            public BracePosition brace_position_for_switch = BracePosition.SAME_LINE;
        }

        /**
         * Configuration for loop formatting.
         */
        @ConfigSerializable
        public static class Loops {

            public boolean insert_space_before_opening_paren_in_for = true;
            public boolean insert_space_after_opening_paren_in_for = false;
            public boolean insert_space_before_colon_in_for = true;
            public boolean insert_space_after_colon_in_for = true;
            public boolean insert_space_before_opening_paren_in_while = true;
            public boolean insert_space_after_opening_paren_in_while = false;
            public boolean insert_space_before_closing_paren_in_while = false;
        }

        /**
         * Configuration for if formatting.
         */
        @ConfigSerializable
        public static class Ifs {

            public boolean insert_space_before_opening_paren_in_if = true;
            public boolean insert_space_after_opening_paren_in_if = false;
            public boolean insert_space_before_closing_paren_in_if = false;
            public boolean insert_new_line_before_else_in_if_statement = false;
        }

        /**
         * Configuration for try catch formatting.
         */
        @ConfigSerializable
        public static class TryCatches {

            public boolean insert_new_line_before_catch_in_try_statement = false;
        }

        /**
         * Configuration for annotation formatting.
         */
        @ConfigSerializable
        public static class Annotations {

            public boolean insert_space_before_closing_paren_in_annotation = false;
            public boolean insert_new_line_after_annotation_on_type = true;
            public boolean insert_new_line_after_annotation_on_parameter = false;
            public boolean insert_new_line_after_annotation_on_package = true;
        }

    }

}
