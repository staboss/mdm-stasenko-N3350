import sys
import json

from docxtpl import DocxTemplate
from docx2pdf import convert


def format_number(number):
    return f'{number:.2f}'.replace('.', ',')


def main():
    internet_cost, minutes_cost, sms_cost = float(sys.argv[1]), float(sys.argv[2]), float(sys.argv[3])

    with open("data.json", 'r', encoding='utf-8') as file:
        content = json.load(file)

    items = [
        {
            "id": "1",
            "description": "Интернет",
            "quantity": "1",
            "unit": "1",
            "price": format_number(internet_cost),
            "amount": format_number(internet_cost)
        },
        {
            "id": "2",
            "description": "Звонки",
            "quantity": "1",
            "unit": "1",
            "price": format_number(minutes_cost),
            "amount": format_number(minutes_cost)
        },
        {
            "id": "3",
            "description": "Смс",
            "quantity": "1",
            "unit": "1",
            "price": format_number(sms_cost),
            "amount": format_number(sms_cost)
        }]

    total_cost = internet_cost + minutes_cost + sms_cost

    content['account']['content'].extend(items)
    content['account']['total'] = format_number(total_cost)
    content['account']['taxes'] = format_number(total_cost * 0.13)

    document = DocxTemplate("template.docx")
    document.render(content)

    name = f"Счет №{content['account']['id']} от {content['account']['date']}.docx"
    document.save(name)
    convert(name)


if __name__ == '__main__':
    if len(sys.argv) == 4:
        main()
